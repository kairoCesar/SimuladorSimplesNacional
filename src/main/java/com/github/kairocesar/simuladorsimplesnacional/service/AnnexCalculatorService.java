package com.github.kairocesar.simuladorsimplesnacional.service;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.model.annexes.*;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnnexCalculatorService {

    private static final double MAXIMUM_ISS_ALIQUOT = 0.05;
    private final List<Annex> annexes = List.of(new AnnexOne(), new AnnexTwo(), new AnnexThree(), new AnnexFour(), new AnnexFive(),
            new CommunicationAndTransport(), new LeasingOfMovableProperty());
    private AnnexRequestDto annexRequestDto;
    Map<String, Double> taxes = new LinkedHashMap<>();


    public AnnexResponseDto getTotalValues(AnnexRequestDto annexRequestDto) {
        this.annexRequestDto = annexRequestDto;
        taxes.clear();
        AnnexResponseDto annexResponseDto = new AnnexResponseDto(calculateTaxes(),
                getEffectiveAliquot(getAnnex()), annexRequestDto.getSalesValue());
        return annexResponseDto.formatResponse();
    }

    public Map<String, Double> calculateTaxes() {
        Map<String, Double[]> taxDistribution = getAnnex().getTaxDistribution(annexRequestDto.isSalesToExterior());
        double effectiveAliquot = getEffectiveAliquot(getAnnex());
        double rbt12 = annexRequestDto.rbt12();
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

    public void calculateIcmsInCommunicationAndTransportService(){
        Annex annex = new AnnexOne();
        double rbt12 = annexRequestDto.rbt12();
        if(getAnnex() instanceof CommunicationAndTransport){
            double aliquotTax = annex.getTaxDistribution(annexRequestDto.isSalesToExterior()).get("ICMS")[getAnnex().getRange(rbt12) - 1] *
                    getEffectiveAliquot(annex);
            double taxValue = annexRequestDto.getSalesValue() * aliquotTax;
            taxes.put("ICMS", taxValue);
        }
    }

    public void calculateTaxesIfIssAliquotIsGreaterThan5() {
        Map<String, Double[]> taxDistribution = getAnnex().getTaxDistribution(annexRequestDto.isSalesToExterior());
        double effectiveAliquot = getEffectiveAliquot(getAnnex());
        Map<String, Double> valuesDistributionLcp123 = getAnnex().getTaxDistributionIfTaxIsGreaterThan5();
        taxDistribution.remove("ISS");
        double effectiveAliquotWithoutIss = effectiveAliquot - MAXIMUM_ISS_ALIQUOT;
        for (Map.Entry<String, Double[]> tax : taxDistribution.entrySet()) {
            double newTaxAliquot = effectiveAliquotWithoutIss * valuesDistributionLcp123.get(tax.getKey());
            double newTaxValue = newTaxAliquot * annexRequestDto.salesValue();
            taxes.put(tax.getKey(), newTaxValue);
        }
        taxes.put("ISS", (annexRequestDto.salesValue()) * MAXIMUM_ISS_ALIQUOT);
    }


    private void distributeReplacedTaxes() {
        for (String tax : annexRequestDto.taxesReplaced()) {
            if (!Objects.isNull(tax) && tax.equalsIgnoreCase("PIS COFINS")) {
                calculateAndReplaceValueOfTax(annexRequestDto.valuePisCofinsReplacement(), "PIS");
                calculateAndReplaceValueOfTax(annexRequestDto.valuePisCofinsReplacement(), "COFINS");
            } else if (!Objects.isNull(tax) && tax.equalsIgnoreCase("ICMS")) {
                calculateAndReplaceValueOfTax(annexRequestDto.valueIcmsReplacement(), tax);
            } else if (!Objects.isNull(tax) && tax.equalsIgnoreCase("ISS")) {
                calculateAndReplaceValueOfTax(annexRequestDto.valueIssReplacement(), tax);
            }
        }
    }

    private void calculateAndReplaceValueOfTax(double salesValue, String tax) {
        Map<String, Double[]> taxDistribution = getAnnex().getTaxDistribution(annexRequestDto.isSalesToExterior());
        Annex annex = getAnnex();
        int range = annex.getRange(annexRequestDto.rbt12());
        double aliquotTax = taxDistribution.get(tax)[range - 1] * getEffectiveAliquot(annex);
        if (getAnnex() instanceof CommunicationAndTransport){
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

    private double getEffectiveAliquot(Annex annex) {
        double deductionValue = annex.getDeductionValue(annex.getRange(annexRequestDto.rbt12()));
        return (((annexRequestDto.rbt12() * annex.getAliquot(annex.getRange(annexRequestDto.rbt12())) -
                deductionValue)) / annexRequestDto.rbt12());
    }
}