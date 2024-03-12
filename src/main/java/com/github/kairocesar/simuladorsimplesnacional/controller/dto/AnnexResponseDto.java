package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexResponseDto {

    private Map<String, Double> taxes;
    double valueAliquotEffective;
    private Map<String, String> taxesToResponse;


    public AnnexResponseDto(Map<String, Double> taxes, double valueAliquotEffective) {
        this.taxes = taxes;
        this.valueAliquotEffective = valueAliquotEffective;
    }

    public AnnexResponseDto(Map<String, String> taxesToResponse) {
        this.taxesToResponse = taxesToResponse;
    }


    public AnnexResponseDto formater() {
        double valueGuide = 0;

        taxesToResponse = new LinkedHashMap<>();
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");

        for (Map.Entry<String, Double> taxToFormat : taxes.entrySet()) {
            valueGuide += taxToFormat.getValue();
            taxesToResponse.put(taxToFormat.getKey(), numberFormat.format(taxToFormat.getValue()));
        }

        taxesToResponse.put("Valor da guia: ", numberFormat.format(valueGuide));
        taxesToResponse.put("Porcentagem aliquota: ", numberFormat.format(valueAliquotEffective));

        return new AnnexResponseDto(taxesToResponse);
    }
    

    public Map<String, String> getTaxesToResponse() {
        return taxesToResponse;
    }

    public void setTaxesToResponse(Map<String, String> taxesToResponse) {
        this.taxesToResponse = taxesToResponse;
    }
}
