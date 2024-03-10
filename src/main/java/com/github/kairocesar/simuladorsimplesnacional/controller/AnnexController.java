package com.github.kairocesar.simuladorsimplesnacional.controller;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexResponseDto;
import com.github.kairocesar.simuladorsimplesnacional.service.AnnexService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annex")
public class AnnexController {

    AnnexService annexService;

    public AnnexController(AnnexService annexService) {
        this.annexService = annexService;
    }

    @PostMapping("/get_values")
    public AnnexResponseDto getValues(@RequestBody AnnexRequestDto annexRequestDto) {
        return annexService.getTotalValues(annexRequestDto);
    }
}
