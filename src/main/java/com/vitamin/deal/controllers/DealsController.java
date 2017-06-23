package com.vitamin.deal.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitamin.deal.domain.Deal;
import com.vitamin.deal.services.DealService;
import com.vitamin.deal.utils.BadDataException;
import com.vitamin.deal.utils.DataNotFoundException;
import com.vitamin.deal.utils.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = { "/api/deals" })
public class DealsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DealsController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DealService service;

    @Autowired
    public DealsController(DealService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Deal> getDeals() {
        return this.service.getDeals();
    }

    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET)
    public Collection<Deal> getDealsForClient(@PathVariable("clientId") final String clientId) {
       return  service.getDealsByClientId(clientId);
    }
    @RequestMapping(value = "/{dealId}", method = RequestMethod.GET)
    public Deal getDeal(@PathVariable("dealId") final String dealId) {
        Optional<Deal> deal = service.get(dealId);
        return deal.orElseThrow(() -> new DataNotFoundException(""));
    }

    @RequestMapping(value = "/{dealId}", method = RequestMethod.DELETE)
    public Deal removeDeal(@PathVariable("dealId") final String dealId) {
        Optional<Deal> deal = service.remove(dealId);
        return deal.orElseThrow(() -> new DataNotFoundException(""));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String addDeal(@RequestBody final String requestBody) {
        try {
            Deal deal = objectMapper.readValue(requestBody, Deal.class);
            return this.service.add(deal);
        }catch (JsonParseException | JsonMappingException je){
            LOGGER.warn(je.getMessage());
            throw new BadDataException();
        }
        catch (IOException e){
            LOGGER.error("IO Error.", e);
            throw new InternalServerException();
        }
    }
}

