package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexFour implements Annex {

    private final Map<String, Double[]> taxes = new LinkedHashMap<>();

    @Override
    public double getAliquot(int range) {
        double[] aliquots = {0.0450, 0.09, 0.1020, 0.14, 0.22, 0.33};
        return aliquots[range - 1];
    }

    @Override
    public double getDeductionValue(int range) {
        double[] deductionValues = {0, 8100.00, 12420.00, 39780.00, 183780.00, 828000.00};
        return deductionValues[range - 1];
    }

    @Override
    public Map<String, Double[]> getTaxDistribution() {
        taxes.put("CSLL", new Double[]{0.1520, 0.1520, 0.1520, 0.1920, 0.1920, 0.2150});
        taxes.put("IRPJ", new Double[]{0.1880, 0.1980, 0.2080, 0.1780, 0.1880, 0.5350});
        taxes.put("PIS", new Double[]{0.0383, 0.0445, 0.0427, 0.0410, 0.0392, 0.0445});
        taxes.put("COFINS", new Double[]{0.1767, 0.2055, 0.1973, 0.1890, 0.1808, 0.2055});
        taxes.put("ISS", new Double[]{0.4450, 0.40, 0.40, 0.40, 0.40, 0.00});
        return taxes;
    }


    public Map<String, Double> getTaxDistributionIfTaxIsGreaterThan5() {
        Map<String, Double> taxesAliquotsRange5IfIssAliquotIsGreaterThan5 = new LinkedHashMap<>();
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("CSLL", 0.32);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("IRPJ", 0.3133);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("PIS", 0.0654);
        taxesAliquotsRange5IfIssAliquotIsGreaterThan5.put("COFINS", 0.3013);
        return taxesAliquotsRange5IfIssAliquotIsGreaterThan5;
    }

}
