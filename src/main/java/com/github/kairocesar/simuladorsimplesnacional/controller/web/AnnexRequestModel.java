package com.github.kairocesar.simuladorsimplesnacional.controller.web;

import java.util.Arrays;

public class AnnexRequestModel {
    private Integer annexOption;
    private String rbt12;
    private String salesValue;
    private String salesValueToExterior;
    private String[] taxesReplaced;
    private String valueIcmsReplacement;
    private String valuePisCofinsReplacement;
    private String valueIssReplacement;

    private String marketOption;

    private boolean multipleAnnex;

    private String valueAmountGuide;

    public AnnexRequestModel() {}

    public AnnexRequestModel(Integer annexOption, String rbt12, String salesValue,
                             String salesValueToExterior, String[] taxesReplaced, String valueIcmsReplacement,
                             String valuePisCofinsReplacement, String valueIssReplacement, String marketOption, boolean multipleAnnex, String valueAmountGuide) {
        this.annexOption = annexOption;
        this.rbt12 = rbt12;
        this.salesValue = salesValue;
        this.salesValueToExterior = salesValueToExterior;
        this.taxesReplaced = taxesReplaced;
        this.valueIcmsReplacement = valueIcmsReplacement;
        this.valuePisCofinsReplacement = valuePisCofinsReplacement;
        this.valueIssReplacement = valueIssReplacement;
        this.marketOption = marketOption;
        this.multipleAnnex = multipleAnnex;
        this.valueAmountGuide = valueAmountGuide;
    }

    public Integer getAnnexOption() {
        return annexOption;
    }

    public void setAnnexOption(Integer annexOption) {
        this.annexOption = annexOption;
    }

    public String getRbt12() {
        return rbt12;
    }

    public void setRbt12(String rbt12) {
        this.rbt12 = rbt12;
    }

    public String getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(String salesValue) {
        this.salesValue = salesValue;
    }

    public String getSalesValueToExterior() {
        return salesValueToExterior;
    }

    public void setSalesValueToExterior(String salesValueToExterior) {
        this.salesValueToExterior = salesValueToExterior;
    }

    public String[] getTaxesReplaced() {
        return taxesReplaced;
    }

    public void setTaxesReplaced(String[] taxesReplaced) {
        this.taxesReplaced = taxesReplaced;
    }

    public String getValueIcmsReplacement() {
        return valueIcmsReplacement;
    }

    public void setValueIcmsReplacement(String valueIcmsReplacement) {
        this.valueIcmsReplacement = valueIcmsReplacement;
    }

    public String getValuePisCofinsReplacement() {
        return valuePisCofinsReplacement;
    }

    public void setValuePisCofinsReplacement(String valuePisCofinsReplacement) {
        this.valuePisCofinsReplacement = valuePisCofinsReplacement;
    }

    public String getValueIssReplacement() {
        return valueIssReplacement;
    }

    public void setValueIssReplacement(String valueIssReplacement) {
        this.valueIssReplacement = valueIssReplacement;
    }

    public String getMarketOption() {
        return marketOption;
    }

    public void setMarketOption(String marketOption) {
        this.marketOption = marketOption;
    }

    public boolean isMultipleAnnex() {
        return multipleAnnex;
    }

    public void setMultipleAnnex(boolean multipleAnnex) {
        this.multipleAnnex = multipleAnnex;
    }

    public String getValueAmountGuide() {
        return valueAmountGuide;
    }

    public void setValueAmountGuide(String valueAmountGuide) {
        this.valueAmountGuide = valueAmountGuide;
    }

}
