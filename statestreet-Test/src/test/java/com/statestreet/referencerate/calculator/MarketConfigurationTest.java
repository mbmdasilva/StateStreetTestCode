package com.statestreet.referencerate.calculator;

import com.statestreet.referencerate.FxPrice;
import com.statestreet.referencerate.PriceProvider;
import com.statestreet.referencerate.PriceSource;
import com.statestreet.referencerate.ReferenceRateCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by mateusdasilva on 11/05/2016.
 */
public class MarketConfigurationTest {

    MarketConfiguration marketConfiguration;

    ConfigurationImpl configuration;

    Map<PriceSource, PriceProvider> map = new HashMap<>();

    List<PriceProvider> priceProviders = Arrays.asList(PriceProvider.PROVIDER1,PriceProvider.PROVIDER2,PriceProvider.PROVIDER3,PriceProvider.PROVIDER4);
    List<PriceSource> priceSources = Arrays.asList(PriceSource.SOURCE1,PriceSource.SOURCE2,PriceSource.SOURCE3,PriceSource.SOURCE4);

    @Before
    public void setup() throws Exception {
        createConfiguration();
        marketConfiguration = new MarketConfiguration(configuration);
    }



    private void createConfiguration() {
        for (int i = 0; i < priceSources.size(); i++) {
            for (int j = 0; j < priceProviders.size(); j++) {
                map.put(priceSources.get(i), priceProviders.get(j));
            }
        }
        configuration = new ConfigurationImpl(map);
    }

}
