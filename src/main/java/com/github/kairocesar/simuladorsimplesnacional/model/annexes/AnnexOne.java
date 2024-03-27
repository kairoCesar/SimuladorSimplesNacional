package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexOne implements Annex {

    private final Map<String, Double[]> taxes = new LinkedHashMap<>();

    @Override
    public double getAliquot(int range) {
        double[] aliquots = {0.04, 0.0730, 0.0950, 0.1070, 0.1430, 0.19};
        return aliquots[range - 1];
    }

    @Override
    public double getDeductionValue(int range) {
        double[] deductionValues = {0, 5940.00, 13860.00, 22500.00, 87300.00, 378000.00};
        return deductionValues[range - 1];
    }

    @Override
    public Map<String, Double[]> getTaxDistribution(boolean isSalesToExterior) {
        taxes.put("CPP", new Double[]{0.4150, 0.4150, 0.42, 0.42, 0.42, 0.4210});
        taxes.put("CSLL", new Double[]{0.0350, 0.0350, 0.0350, 0.0350, 0.0350, 0.10});
        taxes.put("IRPJ", new Double[]{0.0550, 0.0550, 0.0550, 0.0550, 0.0550, 0.1350});
        taxes.put("PIS", new Double[]{0.0276, 0.0276, 0.0276, 0.0276, 0.0276, 0.0613});
        taxes.put("COFINS", new Double[]{0.1274, 0.1274, 0.1274, 0.1274, 0.1274, 0.2827});
        taxes.put("ICMS", new Double[]{0.34, 0.34, 0.3350, 0.3350, 0.3350, 0.00});
        checkSalesToExteriorAndRemoveTaxesPisCofinsIcmsIpi(taxes, isSalesToExterior);
        return taxes;
    }

    @Override
    public Map<String, Double> getTaxDistributionIfTaxIsGreaterThan5() {
        return null;
    }

}
