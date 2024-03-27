package com.github.kairocesar.simuladorsimplesnacional.model.annexes;

import java.util.Map;

public abstract class AnnexAbstract implements Annex {

    public abstract Map<String, Double> getTaxDistributionIfTaxIsGreaterThan5();
}
