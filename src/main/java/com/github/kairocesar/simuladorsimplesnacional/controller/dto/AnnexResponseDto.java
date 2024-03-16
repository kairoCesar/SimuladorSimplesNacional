package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexResponseDto {

    private Map<String, Double> taxes;
    private Map<String, String> dataToResponse;
    private double effectiveAliquot;
    private double salesValue;


    public AnnexResponseDto(Map<String, Double> taxes, double effectiveAliquot, double salesValue) {
        this.taxes = taxes;
        this.effectiveAliquot = effectiveAliquot;
        this.salesValue = salesValue;
    }

    public AnnexResponseDto(Map<String, String> taxesToResponse) {
        this.dataToResponse = taxesToResponse;
    }


    public AnnexResponseDto formatResponse() {
        double totalValueOfTaxes = 0;
        dataToResponse = new LinkedHashMap<>();
        NumberFormat currencyFormat = new DecimalFormat("#,##0.00");

        for (Map.Entry<String, Double> taxToFormat : taxes.entrySet()) {
            totalValueOfTaxes += taxToFormat.getValue();
            dataToResponse.put(taxToFormat.getKey(), currencyFormat.format(taxToFormat.getValue()));
        }

        dataToResponse.put("Valor da guia: ", currencyFormat.format(totalValueOfTaxes));
        dataToResponse.put("Alíquota efetiva: ", String.format("%.3f ",  effectiveAliquot * 100));
        dataToResponse.put("Alíquota líquida: ", String.format("%.3f ", (totalValueOfTaxes / salesValue) * 100));

        return new AnnexResponseDto(dataToResponse);
    }

    public Map<String, String> getDataToResponse() {
        return dataToResponse;
    }
}
