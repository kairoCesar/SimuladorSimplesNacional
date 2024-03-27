package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.Map;

public class CommunicationAndTransport implements Annex{

    private AnnexThree annexThree = new AnnexThree();

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
    public Map<String, Double[]> getTaxDistribution(boolean isSalesToExterior) {
        Map<String, Double[]> taxDistribution = annexThree.getTaxDistribution(isSalesToExterior);
        taxDistribution.remove("ISS");
        taxDistribution.put("ICMS", new Double[]{0.34, 0.34, 0.3350, 0.3350, 0.3350, 0.00});
        checkSalesToExteriorAndRemoveTaxesPisCofinsIcmsIpi(taxDistribution, isSalesToExterior);
        return taxDistribution;
    }

    public AnnexThree getAnnexThree() {
        return annexThree;
    }
}