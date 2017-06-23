package com.vitamin.deal;

import com.vitamin.deal.domain.CompoundInterestRateDeal;
import com.vitamin.deal.domain.SimpleInterestRateDeal;

import java.math.BigDecimal;

/**
 * Created by vitamin on 23/06/2017.
 */
public class TestUtil {
    public static CompoundInterestRateDeal compound(String clientId, BigDecimal principal, String ccy,
                                                     BigDecimal interestRate, int numberOfYear, int compoundRate){
        CompoundInterestRateDeal compoundInterestRateDeal = new CompoundInterestRateDeal(
                clientId, principal, ccy, interestRate, numberOfYear, compoundRate
        );

        return compoundInterestRateDeal;
    }

    public static SimpleInterestRateDeal simple(String clientId, BigDecimal
            principal, String ccy, BigDecimal interestRate, int numberOfYear)
    {
        SimpleInterestRateDeal deal = new SimpleInterestRateDeal(
                clientId, principal, ccy, interestRate, numberOfYear
        );

        return deal;
    }
}
