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
public class CalculatorService {

    public static final double MAXIMUM_ISS_ALIQUOT = 0.05;
    private final List<Annex> annexes = List.of(new AnnexOne(), new AnnexTwo(), new AnnexThree(), new AnnexFour(), new AnnexFive());
    private AnnexRequestDto annexRequestDto;
    Map<String, Double> taxes = new LinkedHashMap<>();

    public AnnexResponseDto getTotalValues(AnnexRequestDto annexRequestDto) {
        this.annexRequestDto = annexRequestDto;
        taxes.clear();
        AnnexResponseDto annexResponseDto = new AnnexResponseDto(calculateTaxes(),
                getEffectiveAliquot(), annexRequestDto.salesValue());
        return annexResponseDto.formatResponse();
    }

    public Map<String, Double> calculateTaxes() {
        double effectiveAliquot = getEffectiveAliquot();
        double rbt12 = annexRequestDto.rbt12();

        for (Map.Entry<String, Double[]> tax : getAnnex().getTaxDistribution().entrySet()) {
            double aliquotTax = tax.getValue()[getAnnex().getRange(rbt12) - 1] *
                    effectiveAliquot;
            double taxValue = annexRequestDto.salesValue() * aliquotTax;
            taxes.put(tax.getKey(), taxValue);
        }

        if (getAnnex().checkAliquotAnnexThreeAndAnnexFour(effectiveAliquot)) calculateTaxesIfIssAliquotIsGreaterThan5();

        if (!Objects.isNull(annexRequestDto.taxesReplaced())) distributeReplacedTaxes();

        return taxes;
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
        Annex annex = getAnnex();
        int range = annex.getRange(annexRequestDto.rbt12());
        double aliquotTax = annex.getTaxDistribution().get(tax)[range - 1] * getEffectiveAliquot();
        double valueTaxToPay = taxes.get(tax);
        double valueTaxToPut = valueTaxToPay - (salesValue * aliquotTax);
        if (getAnnex().checkAliquotAnnexThreeAndAnnexFour(getEffectiveAliquot())) {
            valueTaxToPut = valueTaxToPay - (salesValue * MAXIMUM_ISS_ALIQUOT);
        }
        taxes.put(tax, valueTaxToPut);
    }

    private Annex getAnnex() {
        return annexes.get(annexRequestDto.annexOption() - 1);
    }

    private double getEffectiveAliquot() {
        double deductionValue = getAnnex().getDeductionValue(getAnnex().getRange(annexRequestDto.rbt12()));
        return (((annexRequestDto.rbt12() * getAnnex().getAliquot(getAnnex().getRange(annexRequestDto.rbt12())) -
                deductionValue)) / annexRequestDto.rbt12());
    }

    public void calculateTaxesIfIssAliquotIsGreaterThan5() {
        double effectiveAliquot = getEffectiveAliquot();
        Map<String, Double> valuesDistributionLcp123 = getAnnex().getTaxDistributionIfTaxIsGreaterThan5();
        Map<String, Double[]> annexTaxDistribution = getAnnex().getTaxDistribution();
        annexTaxDistribution.remove("ISS");
        double effectiveAliquotWithoutIss = effectiveAliquot - MAXIMUM_ISS_ALIQUOT;
        for (Map.Entry<String, Double[]> tax : annexTaxDistribution.entrySet()) {
            double newTaxAliquot = effectiveAliquotWithoutIss * valuesDistributionLcp123.get(tax.getKey());
            double newTaxValue = newTaxAliquot * annexRequestDto.salesValue();
            taxes.put(tax.getKey(), newTaxValue);
        }
        taxes.put("ISS", (annexRequestDto.salesValue()) * MAXIMUM_ISS_ALIQUOT);
    }
}