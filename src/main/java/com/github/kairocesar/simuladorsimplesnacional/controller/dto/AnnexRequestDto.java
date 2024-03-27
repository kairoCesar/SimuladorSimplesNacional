package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

import com.github.kairocesar.simuladorsimplesnacional.exceptions.InvalidArgumentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

public record AnnexRequestDto(int annexOption, double rbt12, Double salesValue, Double salesValueToExterior,
                              String[] taxesReplaced, double valueIcmsReplacement, double valuePisCofinsReplacement,
                              double valueIssReplacement) {

    public Double getSalesValue() {
        if (!Objects.isNull(salesValueToExterior)) {
            return salesValueToExterior;
        }
        return salesValue;
    }

    public boolean isSalesToExterior() {
        return !Objects.isNull(salesValueToExterior);
    }

    public String dateOfConsulted() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

//    public void checkForExceptions() {
//
//    }
//
//    private void validateAnnexOption() {
//        if (!Pattern.matches("[1-6]", String.valueOf(annexOption))) {
//            throw new InvalidArgumentException("Por favor, insira um número de 1 a 6");
//        }
//    }
//
//    private void validateRbt12() {
//        if (!Pattern.matches("[1-9]", String.valueOf(this.rbt12))) {
//            throw new InvalidArgumentException("Por favor, insira um valor maior que 0");
//        }
//    }
//
//    private void validateSalesValue() {
//        if (!Pattern.matches("[1-9]", String.valueOf(this.salesValue))
//                && !Pattern.matches("[1-9]", String.valueOf(this.salesValueToExterior))) {
//            throw new InvalidArgumentException("Você precisa informar um valor de vendas válido " +
//                    "para que o cálculo seja feito");
//        }
//    }
//
//    private void checkIfAnyValueIsGreaterThanSalesValue() {
//
//    }
//
//    private void compareValuesWithSalesValues(double salesValue) {
//        Double[] values = {valueIcmsReplacement, valuePisCofinsReplacement, valueIssReplacement};
//        for (Double value : values) {
//            if (value > salesValue) {
//                throw new InvalidArgumentException("Os campos que recebem os valores dos impostos com substituição, retenção" +
//                        "ou tributação monofásica, não podem receber valores maiores que o valor das vendas");
//            }
//        }
//    }
}
