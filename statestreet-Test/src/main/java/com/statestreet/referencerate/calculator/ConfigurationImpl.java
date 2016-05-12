package com.statestreet.referencerate.calculator;

import com.statestreet.referencerate.PriceProvider;
import com.statestreet.referencerate.PriceSource;
import com.statestreet.referencerate.ReferenceRateCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mateusdasilva on 12/05/2016.
 */
public class ConfigurationImpl implements ReferenceRateCalculator.Configuration {

    Map<PriceSource, PriceProvider> map = new HashMap<>();

    public ConfigurationImpl(Map<PriceSource, PriceProvider> map) {
        this.map.putAll(map);
    }

    @Override
    public int getSize() {
        return map.size();
    }

    @Override
    public PriceSource getSource(int index) {
        return null;
    }

    @Override
    public PriceProvider getProvider(int index) {
        return null;
    }
}
