package com.vitamin.deal.domain;

/**
 * Created by vitamin on 22/06/2017.
 */
public enum DealTypes {
    SIMPLE_INTEREST(SimpleInterestRateDeal.class),
    COMPOUND_INTEREST(CompoundInterestRateDeal.class);

    private final Class<?> clazz;

    DealTypes(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
