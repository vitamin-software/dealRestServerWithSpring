package com.vitamin.deal.controllers;

import com.vitamin.deal.domain.Deal;
import com.vitamin.deal.services.DealService;
import com.vitamin.deal.services.FXRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = { "/api/returns" })
public class ReturnsController {

    private final DealService dealService;
    private final FXRateService fxRateService;

    @Autowired
    public ReturnsController(DealService dealService, FXRateService fxRateService) {
        this.dealService = dealService;
        this.fxRateService = fxRateService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public BigDecimal getReturn() {
        Collection<Deal> deals = dealService.getDeals();
        return getAccumulatedReturn(deals);
    }

    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET)
    public BigDecimal getReturnByClient(@PathVariable("clientId") final String clientId) {
        Collection<Deal> deals = dealService.getDealsByClientId(clientId);
        return getAccumulatedReturn(deals);
    }

    protected BigDecimal getAccumulatedReturn(Collection<Deal> deals){
        Map<String, List<Deal>> dealsByCurrency = deals.stream()
                .collect(Collectors.groupingBy(Deal::getCcy));


        BigDecimal accumulated = BigDecimal.ZERO;
        for(Map.Entry<String, List<Deal>> e : dealsByCurrency.entrySet()){
            BigDecimal rate = fxRateService.getFXRate(e.getKey(), "USD");
            Optional<BigDecimal> total = e.getValue().stream()
                    .map(deal -> deal.calculate(rate))
                    .reduce(BigDecimal::add);

            accumulated = total.map(accumulated::add).orElse(accumulated);
        }
        return accumulated;
    }
}
