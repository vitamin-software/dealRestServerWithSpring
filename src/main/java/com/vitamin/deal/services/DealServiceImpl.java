package com.vitamin.deal.services;

import com.vitamin.deal.domain.Deal;
import com.vitamin.deal.repos.DealRepository;
import com.vitamin.deal.utils.BadDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public Optional<Deal> get(String dealId) {
        return dealRepository.get(dealId);
    }

    @Override
    public String add(Deal deal) {
        if(!deal.isValid())
            throw new BadDataException();

        return dealRepository.add(deal);
    }

    @Override
    public Optional<Deal> remove(String dealId) {
        return dealRepository.remove(dealId);
    }

    @Override
    public Collection<Deal> getDealsByClientId(String clientId) {
        return dealRepository.getDealsByClientId(clientId);
    }

    @Override
    public Collection<Deal> getDeals() {
        return dealRepository.getDeals();
    }
}
