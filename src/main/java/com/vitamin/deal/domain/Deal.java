package com.vitamin.deal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(DealTypeIdResolver.class)
public abstract class Deal implements Validable{

    protected String clientId;
    protected BigDecimal principal;
    protected String ccy;

    protected Deal() {}

    public Deal(String clientId, BigDecimal principal, String ccy) {
        this.clientId = clientId;
        this.principal = principal;
        this.ccy = ccy;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    @Override
    @JsonIgnore
    public boolean isValid() {
        return !(StringUtils.isBlank(clientId) ||
                 principal == null ||
                 StringUtils.isBlank(ccy));
    }

    @JsonIgnore
    public abstract String getType();

    public abstract BigDecimal calculate(BigDecimal fxRate);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Deal)) return false;

        Deal deal = (Deal) o;
        return Objects.equals(clientId, deal.clientId) &&
                Objects.equals(principal, deal.principal) &&
                Objects.equals(ccy, deal.ccy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, principal, ccy);
    }
}
