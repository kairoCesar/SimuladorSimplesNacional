package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface Annex {

    double getAliquot(int range);

    double getDeductionValue(int range);

    Map<String, Double[]> getTaxDistribution(boolean isSalesToExterior);

    Map<String, Double> getTaxDistributionIfTaxIsGreaterThan5();

    default int getRange(double rbt12) {
        if (rbt12 <= 180000.00) return 1;
        if (rbt12 <= 360000.00) return 2;
        if (rbt12 <= 720000.00) return 3;
        if (rbt12 <= 1_800_000.00) return 4;
        if (rbt12 <= 3_600_000.00) return 5;
        return 6;
    }

    default boolean checkAliquotAnnexThreeAndAnnexFour(double effectiveAliquot) {
        return this instanceof AnnexThree && (effectiveAliquot * 100) > 14.92537 ||
                this instanceof AnnexFour && (effectiveAliquot * 100) > 12.5;
    }

    default void checkSalesToExteriorAndRemoveTaxesPisCofinsIss(Map<String, Double[]> taxes, boolean isSalesToExterior) {
        if (isSalesToExterior) {
            taxes.put("PIS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            taxes.put("COFINS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            taxes.put("ISS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        }
    }

    default void checkSalesToExteriorAndRemoveTaxesPisCofinsIcmsIpi(Map<String, Double[]> taxes, boolean isSalesToExterior) {
        if (isSalesToExterior) {
            taxes.put("PIS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            taxes.put("COFINS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            taxes.put("ICMS", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            if (this instanceof AnnexTwo) {
                taxes.put("IPI", new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
            }
        }
    }
}
