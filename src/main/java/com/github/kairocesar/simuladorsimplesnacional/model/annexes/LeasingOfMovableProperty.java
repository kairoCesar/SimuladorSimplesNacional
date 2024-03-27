package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.Map;

public class LeasingOfMovableProperty implements Annex {

    AnnexThree annexThree = new AnnexThree();


    @Override
    public double getAliquot(int range) {
        return annexThree.getAliquot(range);
    }

    @Override
    public double getDeductionValue(int range) {
        return annexThree.getDeductionValue(range);
    }

    @Override
    public Map<String, Double[]> getTaxDistribution(boolean isSalesToExterior) {
        Map<String, Double[]> taxes = annexThree.getTaxDistribution(isSalesToExterior);
        taxes.put("ISS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        return taxes;
    }
}
