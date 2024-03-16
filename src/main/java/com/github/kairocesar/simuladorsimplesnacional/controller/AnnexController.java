package com.github.kairocesar.simuladorsimplesnacional.controller;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.service.CalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annex")
public class AnnexController {

    CalculatorService calculatorService;

    public AnnexController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/get_values")
    public AnnexResponseDto getValues(@RequestBody AnnexRequestDto annexRequestDto) {
        return calculatorService.getTotalValues(annexRequestDto);
    }
}
