package com.vitamin.deal.services;


import com.vitamin.deal.domain.FXRate;

import java.math.BigDecimal;

public interface FXRateService {
    boolean updateFXRate(FXRate rate);
    BigDecimal getFXRate(String from, String to);
}
