package com.vitamin.deal.controllers;

import com.vitamin.deal.domain.CompoundInterestRateDeal;
import com.vitamin.deal.domain.FXRate;
import com.vitamin.deal.domain.SimpleInterestRateDeal;
import com.vitamin.deal.repos.DealRepositoryImpl;
import com.vitamin.deal.services.DealService;
import com.vitamin.deal.services.DealServiceImpl;
import com.vitamin.deal.services.FXRateService;
import com.vitamin.deal.services.FXRateServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.vitamin.deal.TestUtil.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ReturnsCalculatorTest {

    @Autowired
    ReturnsController returnsController;

    @Autowired
    private DealService dealService;

    @Autowired
    private FXRateService fxRateService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(this.returnsController).build();
    }

    @Test
    public void testCalculations() throws Exception
    {
        final String client1 = "Client-One";
        final String client2 = "Client-Two";

        CompoundInterestRateDeal compoundOne = compound(client1, new BigDecimal(5000), "EUR",
                new BigDecimal(".05"), 10, 12);

        SimpleInterestRateDeal simpleOne = simple(client1, java.math.BigDecimal.TEN, "EUR",
                new BigDecimal(.05), 5);

        CompoundInterestRateDeal compoundTwo = compound(client2, new BigDecimal(5000), "USD",
                new BigDecimal(".05"), 10, 12);


        final String compoundOneId = this.dealService.add(compoundOne);
        final String simpleOneId = this.dealService.add(simpleOne);
        final String compoundTwoId = this.dealService.add(compoundTwo);

        MvcResult result = mockMvc.perform(get("/api/returns"))
                .andExpect(status().isOk())
                .andReturn();

        String totalValue = result.getResponse().getContentAsString();
        Assert.assertEquals("6472.596", totalValue);

        result = mockMvc.perform(get("/api/returns/client/"+client1))
                .andExpect(status().isOk())
                .andReturn();

        totalValue = result.getResponse().getContentAsString();
        Assert.assertEquals("3237.548", totalValue);

        result = mockMvc.perform(get("/api/returns/client/"+client2))
                .andExpect(status().isOk())
                .andReturn();

        totalValue = result.getResponse().getContentAsString();
        Assert.assertEquals("3235.048", totalValue);

        fxRateService.updateFXRate(new FXRate("EUR", "USD", new BigDecimal("3.5")));
        result = mockMvc.perform(get("/api/returns/client/"+client1))
                .andExpect(status().isOk())
                .andReturn();

        totalValue = result.getResponse().getContentAsString();
        Assert.assertEquals("11331.4180", totalValue);

    }

    @Configuration
    static class ReturnsControllerTestConfiguration{

        @Bean
        public DealService dealService(){
            return new DealServiceImpl(new DealRepositoryImpl());
        }

        @Bean
        public FXRateService fxService(){
            return new FXRateServiceImpl();
        }

        @Bean
        @Autowired
        public ReturnsController returnsControllerController(DealService dealService, FXRateService rateService){
            ReturnsController controller = new ReturnsController(dealService, rateService);
            return controller;
        }
    }

}
