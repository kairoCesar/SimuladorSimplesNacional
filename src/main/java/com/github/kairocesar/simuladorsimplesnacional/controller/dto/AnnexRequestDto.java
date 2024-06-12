package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

import com.github.kairocesar.simuladorsimplesnacional.exceptions.InvalidArgumentException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record AnnexRequestDto(int annexOption, String rbt12, String salesValue, String salesValueToExterior,
                              String[] taxesReplaced, String valueIcmsReplacement, String valuePisCofinsReplacement,
                              String valueIssReplacement) {

    public Double getSalesValue() throws ParseException {

        if (!Objects.isNull(salesValueToExterior)) {
            Number salesValueToExteriorParse = NumberFormat.getNumberInstance(new Locale("pt", "BR"))
                    .parse(salesValueToExterior);
            return Double.parseDouble(salesValueToExteriorParse.toString());
        }
        Number salesValueParse = NumberFormat.getNumberInstance(new Locale("pt", "BR"))
                .parse(salesValue);
        return Double.parseDouble(salesValueParse.toString());
    }

    public boolean isSalesToExterior() {
        return !Objects.isNull(salesValueToExterior);
    }


//    public void checkForExceptions() {
//        validateAnnexOption();
//        validateRbt12();
//        validateSalesValue();
//        compareValuesWithSalesValues();
//    }

    private void validateAnnexOption() {
        if (!Pattern.matches("[1-7]", String.valueOf(annexOption))) {
            throw new InvalidArgumentException("Por favor, insira um número de 1 a 6");
        }
    }

    private void validateRbt12() {
        if (!Pattern.matches("[1-9]", String.valueOf(this.rbt12))) {
            throw new InvalidArgumentException("Por favor, insira um número maior que 0");
        }
    }

    private void validateSalesValue() {
        if (!Pattern.matches("[1-9]", String.valueOf(this.salesValue))
                || !Pattern.matches("[1-9]", String.valueOf(this.salesValueToExterior))) {
            throw new InvalidArgumentException("Por favor, insira somente valores numéricos");
        }
    }

//    private void compareValuesWithSalesValues() {
//        Double[] values = {valueIcmsReplacement, valuePisCofinsReplacement, valueIssReplacement};
//        for (Double value : values) {
//            if (value > getSalesValue()) {
//                throw new InvalidArgumentException("Os campos que recebem os valores dos impostos com substituição, retenção" +
//                        "ou tributação monofásica, não podem receber valores maiores que o valor das vendas");
//            }
//        }
//    }
}
