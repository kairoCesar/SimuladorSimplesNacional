package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexResponseDto {

    private double effectiveAliquot;
    private double salesValue;
    private Map<String, Double> taxesFromService;
    private Map<String, String[]> taxesInfoToResponse = new LinkedHashMap<>();
    private Map<String, String> totalValues = new LinkedHashMap<>();
    private final NumberFormat currencyFormat = new DecimalFormat("#,##0.00");


    public AnnexResponseDto(Map<String, Double> taxes, double effectiveAliquot, double salesValue) {
        this.taxesFromService = taxes;
        this.effectiveAliquot = effectiveAliquot;
        this.salesValue = salesValue;
    }

    public AnnexResponseDto(Map<String, String[]> taxesInfoToResponse, Map<String, String> totalValues) {
        this.taxesInfoToResponse = taxesInfoToResponse;
        this.totalValues = totalValues;
    }

    public AnnexResponseDto formatResponse() {
        formatTaxesInfo();
        return new AnnexResponseDto(taxesInfoToResponse, totalValues);
    }

    private void formatTaxesInfo() {
        double totalValueOfTaxes = 0;
        for (Map.Entry<String, Double> taxToFormat : taxesFromService.entrySet()) {
            totalValueOfTaxes += taxToFormat.getValue();
            double taxValue = taxToFormat.getValue();
            String taxAliquot = String.format("%.4f %%", (taxValue / salesValue) * 100);
            taxesInfoToResponse.put(taxToFormat.getKey(), new String[]{String.format("R$ %s", currencyFormat.format(taxValue)), taxAliquot});
        }
        putAndFormatTotalValues(totalValueOfTaxes);
    }

    private void putAndFormatTotalValues(double totalValueOfTaxes) {
        totalValues.put("Valor da guia: ", String.format("R$ %s", currencyFormat.format(totalValueOfTaxes)));
        totalValues.put("Alíquota efetiva: ", String.format("%.4f %%", effectiveAliquot * 100));
        totalValues.put("Alíquota líquida: ", String.format("%.4f %%", (totalValueOfTaxes / salesValue) * 100));
    }

    public Map<String, String[]> getTaxesInfoToResponse() {
        return taxesInfoToResponse;
    }

    public Map<String, String> getTotalValues() {
        return totalValues;
    }


}
