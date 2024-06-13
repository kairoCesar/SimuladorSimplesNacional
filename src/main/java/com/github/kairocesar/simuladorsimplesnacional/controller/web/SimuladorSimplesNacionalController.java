package com.github.kairocesar.simuladorsimplesnacional.controller.web;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.service.AnnexCalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/")
public class SimuladorSimplesNacionalController {

    private final AnnexCalculatorService annexCalculatorService;

    public SimuladorSimplesNacionalController(AnnexCalculatorService annexCalculatorService) {
        this.annexCalculatorService = annexCalculatorService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView simuladorSimplesNacional(AnnexRequestModel annexRequestModel) {
        ModelAndView modelAndView = new ModelAndView("simples-nacional");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView calcular(AnnexRequestModel annexRequestModel, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return simuladorSimplesNacional(annexRequestModel);
        }

        var response = annexCalculatorService.getTotalValues(
                new AnnexRequestDto(
                        annexRequestModel.getAnnexOption(),
                        annexRequestModel.getRbt12(),
                        annexRequestModel.getSalesValue(),
                        annexRequestModel.getSalesValueToExterior(),
                        annexRequestModel.getTaxesReplaced(),
                        annexRequestModel.getValueIcmsReplacement(),
                        annexRequestModel.getValuePisCofinsReplacement(),
                        annexRequestModel.getValueIssReplacement()
                )
        );

        ModelAndView mv = new ModelAndView("simples-nacional");
        mv.addObject("showTable", true);
        mv.addObject("anexoCalculo", annexRequestModel.getAnnexOption());
        mv.addObject("annexResponseDto", response);
        mv.addObject("mensagem", "Calculado com sucesso.");
        mv.addObject("annexRequestModel", new AnnexRequestModel());

        return mv;
    }

}

