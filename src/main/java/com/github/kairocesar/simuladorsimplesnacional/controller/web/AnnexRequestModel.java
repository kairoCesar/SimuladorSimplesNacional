package com.github.kairocesar.simuladorsimplesnacional.controller.web;

import java.util.Arrays;

public class AnnexRequestModel {
    private Integer annexOption;
    private double rbt12;
    private Double salesValue;
    private Double salesValueToExterior;
    private String[] taxesReplaced;
    private double valueIcmsReplacement;
    private double valuePisCofinsReplacement;
    private double valueIssReplacement;

    public AnnexRequestModel() {}

    public AnnexRequestModel(Integer annexOption, double rbt12, Double salesValue, Double salesValueToExterior, String[] taxesReplaced, double valueIcmsReplacement, double valuePisCofinsReplacement, double valueIssReplacement) {
        this.annexOption = annexOption;
        this.rbt12 = rbt12;
        this.salesValue = salesValue;
        this.salesValueToExterior = salesValueToExterior;
        this.taxesReplaced = taxesReplaced;
        this.valueIcmsReplacement = valueIcmsReplacement;
        this.valuePisCofinsReplacement = valuePisCofinsReplacement;
        this.valueIssReplacement = valueIssReplacement;
    }

    public Integer getAnnexOption() {
        return annexOption;
    }

    public void setAnnexOption(Integer annexOption) {
        this.annexOption = annexOption;
    }

    public double getRbt12() {
        return rbt12;
    }

    public void setRbt12(double rbt12) {
        this.rbt12 = rbt12;
    }

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }

    public Double getSalesValueToExterior() {
        return salesValueToExterior;
    }

    public void setSalesValueToExterior(Double salesValueToExterior) {
        this.salesValueToExterior = salesValueToExterior;
    }

    public String[] getTaxesReplaced() {
        return taxesReplaced;
    }

    public void setTaxesReplaced(String[] taxesReplaced) {
        this.taxesReplaced = taxesReplaced;
    }

    public double getValueIcmsReplacement() {
        return valueIcmsReplacement;
    }

    public void setValueIcmsReplacement(double valueIcmsReplacement) {
        this.valueIcmsReplacement = valueIcmsReplacement;
    }

    public double getValuePisCofinsReplacement() {
        return valuePisCofinsReplacement;
    }

    public void setValuePisCofinsReplacement(double valuePisCofinsReplacement) {
        this.valuePisCofinsReplacement = valuePisCofinsReplacement;
    }

    public double getValueIssReplacement() {
        return valueIssReplacement;
    }

    public void setValueIssReplacement(double valueIssReplacement) {
        this.valueIssReplacement = valueIssReplacement;
    }

}
