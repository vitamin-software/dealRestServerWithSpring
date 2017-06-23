package com.vitamin.deal.domain;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class SimpleInterestRateDeal extends Deal
{
    private int numberOfYear;
    private BigDecimal interestRate;

    private final Supplier<BigDecimal> returnValue;

    public SimpleInterestRateDeal(String clientId, BigDecimal
            principal, String ccy, BigDecimal interestRate, int numberOfYear) {
        super(clientId, principal, ccy);

        this.interestRate = interestRate;
        this.numberOfYear = numberOfYear;
        this.returnValue = Suppliers.memoize(this::calculate);
    }

    public SimpleInterestRateDeal() {
        this.returnValue = Suppliers.memoize(this::calculate);
    }

    private BigDecimal calculate() {
        return this.getPrincipal().multiply(interestRate).multiply(new BigDecimal(numberOfYear));
    }

    @Override
    public String getType() {
        return DealTypes.SIMPLE_INTEREST.name();
    }

    @Override
    public BigDecimal calculate(BigDecimal fxRate) {
        return returnValue.get().multiply(fxRate).setScale(3, RoundingMode.HALF_DOWN);
    }

    public int getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(int numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SimpleInterestRateDeal that = (SimpleInterestRateDeal) o;
        return numberOfYear == that.numberOfYear && Objects.equals(interestRate, that.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfYear, interestRate);
    }
}
