package com.vitamin.deal.services;

import com.vitamin.deal.domain.FXRate;
import com.vitamin.deal.utils.BadDataException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class FXRateServiceImpl implements FXRateService {

    private final ConcurrentMap<String, FXRate> fxRateMap = new ConcurrentHashMap<>();

    @Override
    public boolean updateFXRate(FXRate rate) {
        if(!rate.isValid()){
            throw new BadDataException();
        }

        fxRateMap.put(FXRate.toKey(rate), rate);
        return true;
    }

    @Override
    public BigDecimal getFXRate(String from, String to) {
        return fxRateMap.getOrDefault(FXRate.toKey(from, to), FXRate.DEFAULT_RATE).getRate();
    }
}
