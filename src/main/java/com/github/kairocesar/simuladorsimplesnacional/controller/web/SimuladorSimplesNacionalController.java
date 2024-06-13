package com.github.kairocesar.simuladorsimplesnacional.controller.web;

import com.github.kairocesar.simuladorsimplesnacional.controller.dto.AnnexRequestDto;
import com.github.kairocesar.simuladorsimplesnacional.service.AnnexCalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.text.ParseException;


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

        var annex = new AnnexRequestDto(
                annexRequestModel.getAnnexOption(),
                convertStringToDouble(annexRequestModel.getRbt12()),
                !annexRequestModel.getSalesValue().isBlank() ? convertStringToDouble(annexRequestModel.getSalesValue()) : null,
                !annexRequestModel.getSalesValueToExterior().isBlank() ? convertStringToDouble(annexRequestModel.getSalesValueToExterior()) : null,
                annexRequestModel.getTaxesReplaced(),
                convertStringToDouble(annexRequestModel.getValueIcmsReplacement()),
                convertStringToDouble(annexRequestModel.getValuePisCofinsReplacement()),
                convertStringToDouble(annexRequestModel.getValueIssReplacement())
        );

        System.out.println(annex.annexOption() +" "+ annex.rbt12() +" "+ annex.salesValue()  +" "+ annex.salesValueToExterior() +" "+ annex.valueIcmsReplacement()
                +" "+ annex.salesValueToExterior() +" "+ annex.valueIssReplacement() +" "+ annex.valuePisCofinsReplacement() );

        var response = annexCalculatorService.getTotalValues(annex);

        ModelAndView mv = new ModelAndView("simples-nacional");
        mv.addObject("showTable", true);
        mv.addObject("anexoCalculo", annexRequestModel.getAnnexOption());
        mv.addObject("annexResponseDto", response);
        mv.addObject("mensagem", "Calculado com sucesso.");
        mv.addObject("annexRequestModel", new AnnexRequestModel());

        return mv;

    }

    private static Double convertStringToDouble(String text){
        if(text.isBlank()){
           return converterValorMaskMoneyParaDouble("0");
        }
        return converterValorMaskMoneyParaDouble(text);
    }

    public static Double converterValorMaskMoneyParaDouble(String valorFormatado) {
        // Remove a máscara de formatação monetária (pontos, vírgulas e símbolos)
        String valorSemMascara = valorFormatado.replaceAll("[^\\d-\\+\\.]", "");

        // Converte o valor sem máscara para double e retorna
        return Double.parseDouble(valorSemMascara);
    }

}

