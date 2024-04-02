package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexThree extends AnnexAbstract implements Annex {

    private final Map<String, Double[]> taxes = new LinkedHashMap<>();

    @Override
    public double getAliquot(int range) {
        double[] aliquots = {0.06, 0.1120, 0.1350, 0.16, 0.21, 0.33};
        return aliquots[range - 1];
    }

    @Override
    public double getDeductionValue(int range) {
        double[] deductionValues = {0, 9360.00, 17640.00, 35640.00, 125640.00, 648000.00};
        return deductionValues[range - 1];
    }

    @Override
    public Map<String, Double[]> getTaxDistribution(boolean salesToExterior) {
        taxes.put("CPP", new Double[]{0.4340, 0.4340, 0.4340, 0.4340, 0.4340, 0.3050});
        taxes.put("CSLL", new Double[]{0.0350, 0.0350, 0.0350, 0.0350, 0.0350, 0.15});
        taxes.put("IRPJ", new Double[]{0.04, 0.04, 0.04, 0.04, 0.04, 0.35});
        taxes.put("PIS", new Double[]{0.0278, 0.0305, 0.0296, 0.0296, 0.0278, 0.0347});
        taxes.put("COFINS", new Double[]{0.1282, 0.1405, 0.1364, 0.1364, 0.1282, 0.1603});
        taxes.put("ISS", new Double[]{0.3350, 0.32, 0.3250, 0.3250, 0.3350, 0.00});
        checkSalesToExteriorAndRemoveTaxesPisCofinsIss(taxes, salesToExterior);
        return taxes;
    }

    @Override
    public Map<String, Double> getTaxDistributionIfTaxIsGreaterThan5() {
        Map<String, Double> taxesAliquotsRange5IfIssAliquotIsGreaterThan5 = new LinkedHashMap<>();
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("CPP", 0.6526);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("CSLL", 0.0526);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("IRPJ", 0.0602);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("PIS", 0.0418);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("COFINS", 0.1928);
        return taxesAliquotsRange5IfIssAliquotIsGreaterThan5;
    }

}
