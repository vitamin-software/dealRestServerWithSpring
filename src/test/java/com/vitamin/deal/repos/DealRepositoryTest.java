package com.vitamin.deal.repos;

import com.vitamin.deal.domain.Deal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.vitamin.deal.TestUtil.*;

public class DealRepositoryTest {

    private DealRepository dealRepository;
    @Before
    public void setUp() throws Exception {
        dealRepository = new DealRepositoryImpl();
    }

    @Test
    public void testAddingDeals()
    {
        final String clientId = "Client-Test";
        BigDecimal interestRate = new BigDecimal(".05");

        Deal compound = compound(clientId, BigDecimal.TEN, "USD", interestRate, 5, 12);
        Deal simple = simple(clientId, BigDecimal.TEN, "USD", interestRate, 5);

        String compoundId = dealRepository.add(compound);
        String simpleId = dealRepository.add(simple);

        Assert.assertEquals(2, dealRepository.getDeals().size());
        Assert.assertEquals(2, dealRepository.getDealsByClientId(clientId).size());

        dealRepository.remove(simpleId);
        Assert.assertEquals(1, dealRepository.getDeals().size());
        Assert.assertEquals(1, dealRepository.getDealsByClientId(clientId).size());


        simple = simple("Client888-Test", BigDecimal.TEN, "USD", interestRate, 5);
        simpleId = dealRepository.add(simple);
        Assert.assertEquals(2, dealRepository.getDeals().size());
        Assert.assertEquals(1, dealRepository.getDealsByClientId(clientId).size());

        Assert.assertNotNull(dealRepository.get(compoundId));
        Assert.assertNotNull(dealRepository.get(simpleId));
    }

    @Test
    public void testNotExistingRemove()
    {
        Assert.assertEquals(Optional.empty(), dealRepository.remove("Foo"));
    }

    @Test
    public void testNotExistingGet()
    {
        Assert.assertEquals(Optional.empty(), dealRepository.get("Foo"));
    }
}
