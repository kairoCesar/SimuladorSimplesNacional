package com.github.kairocesar.simuladorsimplesnacional.service;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.model.annexes.Annex;
import com.github.kairocesar.simuladorsimplesnacional.model.annexes.AnnexOne;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnnexService {

    private final List<Annex> annexes = List.of(new AnnexOne());
    private double valueGuide;
    private double valueAliquotEffective;
    private AnnexRequestDto annexRequestDto;
    Map<String, Double> taxes = new LinkedHashMap<>();

    public AnnexResponseDto getTotalValues(AnnexRequestDto annexRequestDto){
        this.annexRequestDto = annexRequestDto;
        return new AnnexResponseDto(calculateTaxes(), valueGuide,
                valueAliquotEffective).formatResult();
    }

    public Map<String, Double> calculateTaxes() {
        Annex annex = annexes.get(annexRequestDto.annexOption() - 1);
        double rbt12 = annexRequestDto.rbt12();

        for (Map.Entry<String, Double[]> tax : annex.getTaxDistribution().entrySet()) {
            double aliquotTax = annex.getAliquot(annex.getRange(rbt12))
                    * tax.getValue()[annex.getRange(rbt12)];
            double taxValue = annexRequestDto.salesValue() * aliquotTax;
            taxes.put(tax.getKey(), taxValue);
            valueGuide += taxValue;
            valueAliquotEffective += (aliquotTax * 100);
        }
        if (!Objects.isNull(annexRequestDto.taxesReplaced())) {
            calculateTaxWithReplacement();
        }
        return taxes;
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
        double aliquotTax = taxes.get(tax) / salesValue;
        double valueTaxToPay = taxes.get(tax);
        double valueTaxToPut = valueTaxToPay - (salesValue * aliquotTax);
        taxes.put(tax, valueTaxToPut);
    }

}
