package com.vitamin.deal.domain;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class CompoundInterestRateDeal extends Deal
{
    private BigDecimal interestRate; // r
    private int numberOfYear; //t
    private int compoundRate; // n

    private final Supplier<BigDecimal> returnValue;

    public CompoundInterestRateDeal(String clientId, BigDecimal principal, String ccy,
                                    BigDecimal interestRate, int numberOfYear, int compoundRate) {
        super(clientId, principal, ccy);
        this.interestRate = interestRate;
        this.numberOfYear = numberOfYear;
        this.compoundRate = compoundRate;

        this.returnValue = Suppliers.memoize(this::calculate);
    }

    public CompoundInterestRateDeal() {
        this.returnValue = Suppliers.memoize(this::calculate);
    }

    @Override
    public String getType() {
        return DealTypes.COMPOUND_INTEREST.name();
    }

    private BigDecimal calculate() {
        BigDecimal tmp = BigDecimal.ONE.add(
                interestRate.divide(new BigDecimal(compoundRate), 9, RoundingMode.HALF_DOWN)
        ).pow(numberOfYear * compoundRate);

        return this.getPrincipal().multiply(tmp).add(this.getPrincipal().negate())
                .setScale(3, RoundingMode.HALF_DOWN);
    }

    @Override
    public BigDecimal calculate(BigDecimal fxRate) {
        return returnValue.get().multiply(fxRate);
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public int getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(int numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public int getCompoundRate() {
        return compoundRate;
    }

    public void setCompoundRate(int compoundRate) {
        this.compoundRate = compoundRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompoundInterestRateDeal that = (CompoundInterestRateDeal) o;
        return numberOfYear == that.numberOfYear &&
                compoundRate == that.compoundRate &&
                Objects.equals(interestRate, that.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate, numberOfYear, compoundRate);
    }
}
