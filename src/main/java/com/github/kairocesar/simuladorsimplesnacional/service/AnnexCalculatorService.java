package com.github.kairocesar.simuladorsimplesnacional.service;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.model.annexes.*;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class AnnexCalculatorService {

    private static final double MAXIMUM_ISS_ALIQUOT = 0.05;
    private final List<Annex> annexes = List.of(new AnnexOne(), new AnnexTwo(), new AnnexThree(), new AnnexFour(), new AnnexFive(),
            new CommunicationAndTransport(), new LeasingOfMovableProperty());
    private AnnexRequestDto annexRequestDto;
    Map<String, Double> taxes = new LinkedHashMap<>();


    public AnnexResponseDto getTotalValues(AnnexRequestDto annexRequestDto) throws ParseException {
        this.annexRequestDto = annexRequestDto;
        taxes.clear();
        AnnexResponseDto annexResponseDto = new AnnexResponseDto(calculateTaxes(),
                getEffectiveAliquot(getAnnex()), annexRequestDto.getSalesValue(), getAnnex().getRange(convertToDouble(annexRequestDto.rbt12())));
        return annexResponseDto.formatResponse();
    }

    public Double convertToDouble(String value) throws ParseException {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        Number numberRbt12 = formatter.parse(value);
        return Double.parseDouble(numberRbt12.toString());
    }

    public Map<String, Double> calculateTaxes() throws ParseException {
        Map<String, Double[]> taxDistribution = getAnnex().getTaxDistribution(annexRequestDto.isSalesToExterior());
        double effectiveAliquot = getEffectiveAliquot(getAnnex());
        double rbt12 = convertToDouble(annexRequestDto.rbt12());
        for (Map.Entry<String, Double[]> tax : taxDistribution.entrySet()) {
            double aliquotTax = tax.getValue()[getAnnex().getRange(rbt12) - 1] *
                    effectiveAliquot;
            double taxValue = annexRequestDto.getSalesValue() * aliquotTax;
            taxes.put(tax.getKey(), taxValue);
        }

        calculateIcmsInCommunicationAndTransportService();

        if (getAnnex().checkAliquotAnnexThreeAndAnnexFour(effectiveAliquot))
            calculateTaxesIfIssAliquotIsGreaterThan5();

        if (!Objects.isNull(annexRequestDto.taxesReplaced()))
            distributeReplacedTaxes();

        return taxes;
    }

    public void calculateIcmsInCommunicationAndTransportService() throws ParseException {
        if (getAnnex() instanceof CommunicationAndTransport) {
            Annex annex = new AnnexOne();
            double rbt12 = convertToDouble(annexRequestDto.rbt12());
            double aliquotTax = annex.getTaxDistribution(annexRequestDto.isSalesToExterior()).get("ICMS")[getAnnex().getRange(rbt12) - 1] *
                    getEffectiveAliquot(annex);
            double taxValue = annexRequestDto.getSalesValue() * aliquotTax;
            taxes.put("ICMS", taxValue);
        }
    }

    public void calculateTaxesIfIssAliquotIsGreaterThan5() throws ParseException {
        Double salesValueToExterior = convertToDouble(annexRequestDto.salesValueToExterior());
        Double salesValue = convertToDouble(annexRequestDto.salesValue());

        AnnexAbstract annexAbstract = new AnnexThree();
        double validSalesValue = ((annexRequestDto.isSalesToExterior()) ? salesValueToExterior : salesValue);
        Map<String, Double[]> taxDistribution = getAnnex().getTaxDistribution(annexRequestDto.isSalesToExterior());
        double effectiveAliquot = getEffectiveAliquot(getAnnex());
        if (getAnnex() instanceof AnnexFour) {
            annexAbstract = new AnnexFour();
        }
        Map<String, Double> valuesDistributionLcp123 = annexAbstract.getTaxDistributionIfTaxIsGreaterThan5();
        taxDistribution.remove("ISS");
        double effectiveAliquotWithoutIss = effectiveAliquot - MAXIMUM_ISS_ALIQUOT;
        for (Map.Entry<String, Double[]> tax : taxDistribution.entrySet()) {
            double newTaxAliquot = effectiveAliquotWithoutIss * valuesDistributionLcp123.get(tax.getKey());
            double newTaxValue = newTaxAliquot * validSalesValue;
            taxes.put(tax.getKey(), newTaxValue);
        }
        taxes.put("ISS", (validSalesValue * MAXIMUM_ISS_ALIQUOT));

        if (annexRequestDto.isSalesToExterior()) {
            taxes.put("ISS", 0.00);
            taxes.put("PIS", 0.00);
            taxes.put("COFINS", 0.00);
        }
    }


    private void distributeReplacedTaxes() throws ParseException {

        for (String tax : annexRequestDto.taxesReplaced()) {
            if (!Objects.isNull(tax) && tax.equalsIgnoreCase("PIS COFINS")) {
                calculateAndReplaceValueOfTax(convertToDouble(annexRequestDto.valuePisCofinsReplacement()), "PIS");
                calculateAndReplaceValueOfTax(convertToDouble(annexRequestDto.valuePisCofinsReplacement()), "COFINS");
            } else if (!Objects.isNull(tax) && tax.equalsIgnoreCase("ICMS")) {
                calculateAndReplaceValueOfTax(convertToDouble(annexRequestDto.valueIcmsReplacement()), tax);
            } else if (!Objects.isNull(tax) && tax.equalsIgnoreCase("ISS")) {
                calculateAndReplaceValueOfTax(convertToDouble(annexRequestDto.valueIssReplacement()), tax);
            }
        }
    }

    private void calculateAndReplaceValueOfTax(double salesValue, String tax) throws ParseException {
        Map<String, Double[]> taxDistribution = getAnnex().getTaxDistribution(annexRequestDto.isSalesToExterior());
        Annex annex = getAnnex();
        int range = annex.getRange(convertToDouble(annexRequestDto.rbt12()));
        double aliquotTax = taxDistribution.get(tax)[range - 1] * getEffectiveAliquot(annex);
        if (getAnnex() instanceof CommunicationAndTransport) {
            aliquotTax = taxDistribution.get("ICMS")[range - 1] *
                    getEffectiveAliquot(new AnnexOne());
        }
        double valueTaxToPay = taxes.get(tax);
        double valueTaxToPut = valueTaxToPay - (salesValue * aliquotTax);
        if (getAnnex().checkAliquotAnnexThreeAndAnnexFour(getEffectiveAliquot(annex))) {
            valueTaxToPut = valueTaxToPay - (salesValue * MAXIMUM_ISS_ALIQUOT);
        }

        taxes.put(tax, valueTaxToPut);
    }


    private Annex getAnnex() {
        return annexes.get(annexRequestDto.annexOption() - 1);
    }

    private double getEffectiveAliquot(Annex annex) throws ParseException {
        Double rbt12 = convertToDouble(annexRequestDto.rbt12());
        double deductionValue = annex.getDeductionValue(annex.getRange(rbt12));
        return (((rbt12 * annex.getAliquot(annex.getRange(rbt12)) - deductionValue)) / rbt12);
    }
}