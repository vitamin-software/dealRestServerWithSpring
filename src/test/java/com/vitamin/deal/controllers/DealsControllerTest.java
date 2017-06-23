package com.vitamin.deal.controllers;

import com.vitamin.deal.repos.DealRepositoryImpl;
import com.vitamin.deal.services.DealService;
import com.vitamin.deal.services.DealServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DealsControllerTest {

    @Autowired
    DealsController dealsController;

    @Autowired
    private DealService dealService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(this.dealsController).build();
    }

    @Test
    public void testAddDeal() throws Exception{
        String json = "{\"type\":\"COMPOUND_INTEREST\",\"clientId\":\"Client-Test\",\"principal\":5000,\"ccy\":\"USD\",\"interestRate\":0.05,\"numberOfYear\":10,\"compoundRate\":12}";

        MvcResult result = mockMvc.perform(put("/api/deals").content(json))
                .andExpect(status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        result = mockMvc.perform(get("/api/deals/"+id))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(json, result.getResponse().getContentAsString());

        result = mockMvc.perform(get("/api/deals/client/Client-Test"))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals("["+json+"]", result.getResponse().getContentAsString());
    }

    @Test
    public void testGetUnknownDeal() throws Exception{

        MvcResult result = mockMvc.perform(get("/api/deals/Foo"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testDeleteUnknownDeal() throws Exception{

        MvcResult result = mockMvc.perform(delete("/api/deals/Foo"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Configuration
    static class DealControllerTestConfiguration{

        @Bean
        public DealService dealService(){
            return new DealServiceImpl(new DealRepositoryImpl());
        }

        @Bean
        @Autowired
        public DealsController dealsController(DealService dealService){
            DealsController controller = new DealsController(dealService);
            return controller;
        }
    }

}
