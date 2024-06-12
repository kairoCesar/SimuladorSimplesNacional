package com.github.kairocesar.simuladorsimplesnacional.controller;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.service.AnnexCalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/annex")
public class AnnexController {

    private final AnnexCalculatorService annexCalculatorService;


    public AnnexController(AnnexCalculatorService annexCalculatorService) {
        this.annexCalculatorService = annexCalculatorService;
    }

    @PostMapping("/get_values")
    public AnnexResponseDto getValues(@RequestBody AnnexRequestDto annexRequestDto) throws ParseException {
        return annexCalculatorService.getTotalValues(annexRequestDto);
    }


}
