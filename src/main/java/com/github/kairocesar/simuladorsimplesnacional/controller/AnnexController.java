package com.github.kairocesar.simuladorsimplesnacional.controller;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.service.AnnexCalculatorService;
import com.github.kairocesar.simuladorsimplesnacional.service.DataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annex")
public class AnnexController {

    private final AnnexCalculatorService annexCalculatorService;
    private final DataService dataService;


    public AnnexController(AnnexCalculatorService annexCalculatorService, DataService dataService) {
        this.annexCalculatorService = annexCalculatorService;
        this.dataService = dataService;
    }

    @PostMapping("/get_values")
    public AnnexResponseDto getValues(@RequestBody AnnexRequestDto annexRequestDto) {
        return annexCalculatorService.getTotalValues(annexRequestDto, dataService);
    }


}
