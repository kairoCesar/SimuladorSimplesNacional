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
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class SimuladorSimplesNacionalController {

    private final AnnexCalculatorService annexCalculatorService;

    public SimuladorSimplesNacionalController(AnnexCalculatorService annexCalculatorService) {
        this.annexCalculatorService = annexCalculatorService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView simuladorSimplesNacional(AnnexRequestModel annexRequestModel) {
        annexRequestModel.setMarketOption("interno");
        ModelAndView modelAndView = new ModelAndView("simples-nacional");
        modelAndView.addObject("showTable", false);
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView calcular(AnnexRequestModel annexRequestModel, BindingResult result, RedirectAttributes attributes) {
        var taxes = taxesReplacedFields(annexRequestModel);
        annexRequestModel.setTaxesReplaced(taxes);

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

        var response = annexCalculatorService.getTotalValues(annex);

        ModelAndView mv = new ModelAndView("simples-nacional");
        mv.addObject("showTable", true);
        mv.addObject("annexResponseDto", response);
        mv.addObject("mensagem", "Calculado com sucesso.");
        mv.addObject("annexRequestModel", annexRequestModel);

        return mv;

    }

    private static Double convertStringToDouble(String text){
        if(text.isBlank()){
           return converterValorMaskMoneyParaDouble("0");
        }
        return converterValorMaskMoneyParaDouble(text);
    }

    private static Double converterValorMaskMoneyParaDouble(String valorFormatado) {
        valorFormatado = valorFormatado.replaceAll("\\.", "TEMP_POINT")
                .replaceAll(",", ".")
                .replaceAll("TEMP_POINT", ",");

        // Remove a máscara de formatação monetária (pontos, vírgulas e símbolos)
        String valorSemMascara = valorFormatado.replaceAll("[^\\d-\\+\\.]", "");

        // Converte o valor sem máscara para double e retorna
        return Double.parseDouble(valorSemMascara);
    }

    private String[] taxesReplacedFields(AnnexRequestModel annexRequestModel) {

        List<String> taxesList = new ArrayList<>();

        if(!annexRequestModel.getValueIcmsReplacement().isBlank()) {
            taxesList.add("ICMS");
        }

        if(!annexRequestModel.getValuePisCofinsReplacement().isBlank()){
            taxesList.add("PIS COFINS");
        }

        if(!annexRequestModel.getValueIssReplacement().isBlank()) {
            taxesList.add("ISS");
        }

        String[] taxes = taxesList.toArray(new String[0]);

        return taxes;
    }

}

