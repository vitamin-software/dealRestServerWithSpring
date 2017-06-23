package com.vitamin.deal.domain;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class FXRate implements Validable {
    public final static FXRate DEFAULT_RATE = new FXRate("USD", "USD", BigDecimal.ONE);

    private String from;
    private String to;
    private BigDecimal rate;

    public FXRate(String from, String to, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    protected FXRate() {}

    @Override
    public boolean isValid() {
        return !(StringUtils.isBlank(from) ||
                 StringUtils.isBlank(to) ||
                 rate == null ||
                 BigDecimal.ZERO.equals(rate));
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public static String toKey(FXRate rate){
        return toKey(rate.from, rate.to);
    }

    public static String toKey(String from, String to){
        return from + "." + to;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FXRate fxRate = (FXRate) o;
        return Objects.equals(from, fxRate.from) &&
                Objects.equals(to, fxRate.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
