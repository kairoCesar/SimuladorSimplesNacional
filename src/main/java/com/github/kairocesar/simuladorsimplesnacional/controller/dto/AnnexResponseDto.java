package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

public record AnnexResponseDto(Map<String, Double> taxes,double valueGuide,
                               double valueAliquotEffective) {

    public AnnexResponseDto formatResult(){
        String valueTotal = DecimalFormat.getInstance().format(valueGuide);
        return new AnnexResponseDto(taxes, Double.parseDouble(valueTotal), valueAliquotEffective);
    }


}
