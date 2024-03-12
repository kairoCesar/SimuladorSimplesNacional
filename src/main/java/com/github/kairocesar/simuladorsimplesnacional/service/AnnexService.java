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
public class AnnexService {

    private final List<Annex> annexes = List.of(new AnnexOne(), new AnnexTwo(), new AnnexThree(), new AnnexFour(), new AnnexFive());
    private double valueAliquotEffective;
    private AnnexRequestDto annexRequestDto;
    Map<String, Double> taxes = new LinkedHashMap<>();

    public AnnexResponseDto getTotalValues(AnnexRequestDto annexRequestDto) {
        this.annexRequestDto = annexRequestDto;
        AnnexResponseDto annexResponseDto = new AnnexResponseDto(calculateTaxes(), valueAliquotEffective);
        return annexResponseDto.formater();
    }

    public Map<String, Double> calculateTaxes() {
        Annex annex = getAnnex();
        double rbt12 = annexRequestDto.rbt12();
        double sumAliquot = 0;

        for (Map.Entry<String, Double[]> tax : annex.getTaxDistribution().entrySet()) {
            double aliquotTax = annex.getAliquot(annex.getRange(rbt12))
                    * tax.getValue()[annex.getRange(rbt12) - 1];
            double taxValue = annexRequestDto.salesValue() * aliquotTax;
            taxes.put(tax.getKey(), taxValue);
            sumAliquot += (aliquotTax * 100);
        }
        if (!Objects.isNull(annexRequestDto.taxesReplaced())) {
            calculateTaxWithReplacement();
        }
        valueAliquotEffective = sumAliquot;
        return taxes;
    }

    private Annex getAnnex() {
        return annexes.get(annexRequestDto.annexOption() - 1);
    }

    private void calculateTaxWithReplacement() {
        for (String tax : annexRequestDto.taxesReplaced()) {
            if (!Objects.isNull(tax) && tax.equalsIgnoreCase("PIS COFINS")) {
                removeValueOfTax(annexRequestDto.valuePisCofinsReplacement(), "PIS");
                removeValueOfTax(annexRequestDto.valuePisCofinsReplacement(), "COFINS");
            } else if (!Objects.isNull(tax) && tax.equalsIgnoreCase("ICMS")) {
                removeValueOfTax(annexRequestDto.valueIcmsReplacement(), tax);
            } else if (!Objects.isNull(tax) && tax.equalsIgnoreCase("ISS")) {
                removeValueOfTax(annexRequestDto.valueIssReplacement(), tax);
            }
        }
    }

    private void removeValueOfTax(double salesValue, String tax) {
        Annex annex = getAnnex();
        int range = annex.getRange(annexRequestDto.rbt12());
        double aliquotTax = annex.getAliquot(range) * annex.getTaxDistribution().get(tax)[range - 1];
        double valueTaxToPay = taxes.get(tax);
        double valueTaxToPut = valueTaxToPay - (salesValue * aliquotTax);
        taxes.put(tax, valueTaxToPut);
    }
}