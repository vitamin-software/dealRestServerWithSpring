package com.vitamin.deal.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static com.vitamin.deal.TestUtil.*;

public class CompoundInterestRateDealTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCalculation(){
        CompoundInterestRateDeal deal = compound("Client-Test", new BigDecimal(5000), "USD",
                new BigDecimal(".05"), 10, 12);

        Assert.assertEquals(0, new BigDecimal("3235.048").compareTo(deal.calculate(BigDecimal.ONE)) );
        Assert.assertEquals(0, new BigDecimal("6470.096").compareTo(deal.calculate(new BigDecimal(2))) );
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        CompoundInterestRateDeal deal = compound("Client-Test", new BigDecimal(5000), "USD",
                new BigDecimal(".05"), 10, 12);

        String expected = "{\"type\":\"COMPOUND_INTEREST\",\"clientId\":\"Client-Test\",\"principal\":5000,\"ccy\":\"USD\",\"interestRate\":0.05,\"numberOfYear\":10,\"compoundRate\":12}";
        Assert.assertEquals(expected, objectMapper.writeValueAsString(deal));
    }

    @Test
    public void testFromJson() throws IOException {

        CompoundInterestRateDeal expected = compound("Client-Test", new BigDecimal(5000), "USD",
                new BigDecimal(".05"), 10, 12);
        String json = "{\"type\":\"COMPOUND_INTEREST\",\"clientId\":\"Client-Test\",\"principal\":5000,\"ccy\":\"USD\",\"interestRate\":0.05,\"numberOfYear\":10,\"compoundRate\":12}";

        Deal deal = objectMapper.readValue(json, Deal.class);

        Assert.assertEquals(expected, deal);
    }

    @Test
    public void testEqualityAndHashcode(){
        CompoundInterestRateDeal dealOne = compound("Client-Test", new BigDecimal(5000), "USD",
                new BigDecimal(.05), 10, 12);


        CompoundInterestRateDeal dealTwo = compound("Client-Test", new BigDecimal(5000), "USD",
                new BigDecimal(.05), 10, 12);

        Assert.assertTrue(dealOne.equals(dealTwo));
        Assert.assertTrue(dealTwo.equals(dealOne));

        Assert.assertEquals(dealOne.hashCode(), dealTwo.hashCode());
    }
}
