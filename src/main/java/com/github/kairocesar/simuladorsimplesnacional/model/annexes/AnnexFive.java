package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnnexFive implements Annex {

    private final Map<String, Double[]> taxes = new LinkedHashMap<>();

    @Override
    public double getAliquot(int range) {
        double[] aliquots = {15.50, 18, 19.50, 20.50, 23, 30.50};
        return aliquots[range - 1];
    }

    @Override
    public double getDeductionValue(int range) {
        double[] deductionValues = {0, 4500, 9900, 17100, 62100, 540000};
        return deductionValues[range - 1];
    }

    @Override
    public Map<String, Double[]> getTaxDistribution() {
        taxes.put("CPP", new Double[]{0.2885, 0.2785, 0.2385, 0.2385, 0.2385, 0.2950});
        taxes.put("CSLL", new Double[]{0.15, 0.15, 0.15, 0.15, 0.1250, 0.1550});
        taxes.put("IRPJ", new Double[]{0.25, 0.23, 0.24, 0.21, 0.23, 0.35});
        taxes.put("PIS", new Double[]{0.0305, 0.0305, 0.0323, 0.0341, 0.0305, 0.0356});
        taxes.put("COFINS", new Double[]{0.1410, 0.1410, 0.1492, 0.1574, 0.1410, 0.1644});
        taxes.put("ISS", new Double[]{0.14, 0.17, 0.19, 0.21, 0.2350, 0.2350});
        return taxes;
    };
}
