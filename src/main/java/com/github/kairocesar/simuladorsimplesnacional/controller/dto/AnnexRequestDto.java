package com.github.kairocesar.simuladorsimplesnacional.controller.dto;

public record AnnexRequestDto(int annexOption, double rbt12, double salesValue, String[] taxesReplaced,
                              double valueIcmsReplacement, double valuePisCofinsReplacement,
                              double valueIssReplacement, double valuePayRoll){

}
