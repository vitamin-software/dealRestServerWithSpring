package com.vitamin.deal.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class SimpleInterestRateDealTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCalculation(){
        SimpleInterestRateDeal deal = new SimpleInterestRateDeal(
                "Client-Test", java.math.BigDecimal.TEN, "EUR", new BigDecimal(.05), 5
        );

        Assert.assertEquals(0, new BigDecimal(2.5).compareTo(deal.calculate(BigDecimal.ONE)) );
        Assert.assertEquals(0, new BigDecimal(5.0).compareTo(deal.calculate(new BigDecimal(2))) );
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        SimpleInterestRateDeal deal = new SimpleInterestRateDeal(
                "Client-Test", java.math.BigDecimal.TEN, "EUR", new BigDecimal(.5), 5
        );

        String expected = "{\"type\":\"SIMPLE_INTEREST\",\"clientId\":\"Client-Test\",\"principal\":10,\"ccy\":\"EUR\",\"numberOfYear\":5,\"interestRate\":0.5}";
        Assert.assertEquals(expected, objectMapper.writeValueAsString(deal));
    }

    @Test
    public void testFromJson() throws IOException {

        SimpleInterestRateDeal expected = new SimpleInterestRateDeal(
                "Client-Test", java.math.BigDecimal.TEN, "EUR", new BigDecimal(.5), 5
        );

        String json = "{\"type\":\"SIMPLE_INTEREST\",\"clientId\":\"Client-Test\",\"principal\":10,\"ccy\":\"EUR\",\"numberOfYear\":5,\"interestRate\":0.5}";
        Deal deal = objectMapper.readValue(json, Deal.class);

        Assert.assertEquals(expected, deal);
    }

    @Test
    public void testEqualityAndHashcode(){
        SimpleInterestRateDeal dealOne = new SimpleInterestRateDeal(
                "Client-Test", java.math.BigDecimal.TEN, "EUR", new BigDecimal(.5), 5
        );

        SimpleInterestRateDeal dealTwo = new SimpleInterestRateDeal(
                "Client-Test", java.math.BigDecimal.TEN, "EUR", new BigDecimal(.5), 5
        );

        Assert.assertTrue(dealOne.equals(dealTwo));
        Assert.assertTrue(dealTwo.equals(dealOne));

        Assert.assertEquals(dealOne.hashCode(), dealTwo.hashCode());
    }
}
