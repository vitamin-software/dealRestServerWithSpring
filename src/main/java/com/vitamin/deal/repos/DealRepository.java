package com.vitamin.deal.repos;


import com.vitamin.deal.domain.Deal;

import java.util.Collection;
import java.util.Optional;

public interface DealRepository {
    Optional<Deal> get(String dealId);
    String add(Deal deal);
    Optional<Deal> remove(String dealId);
    Collection<Deal> getDealsByClientId(String clientId);
    Collection<Deal> getDeals();
}
