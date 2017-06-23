package com.vitamin.deal.controllers;


import com.vitamin.deal.domain.FXRate;
import com.vitamin.deal.services.FXRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = { "/api/reference" })
public class ReferenceDataController {

    private final FXRateService fxRateService;

    @Autowired
    public ReferenceDataController(FXRateService fxRateService) {
        this.fxRateService = fxRateService;
    }

    @RequestMapping(value = "/fxRate", method = RequestMethod.PUT)
    public void addSimpleInterestDeal(@RequestBody final FXRate fxRate) {
        fxRateService.updateFXRate(fxRate);
    }
}
