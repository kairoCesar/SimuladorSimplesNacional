package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface Annex {

    default int getRange(double rbt12) {
        if (rbt12 <= 180000.00) return 1;
        if (rbt12 <= 360000.00) return  2;
        if (rbt12 <= 720000.00) return 3;
        if (rbt12 <= 1_800_000.00) return 4;
        if (rbt12 <= 3_600_000.00) return 5;
        return 6;
    }

    double getAliquot(int range);

    double getDeductionValue(int range);

    Map<String, Double[]> getTaxDistribution();
}
