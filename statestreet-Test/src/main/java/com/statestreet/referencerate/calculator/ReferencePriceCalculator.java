package com.statestreet.referencerate.calculator;

import com.statestreet.referencerate.FxPrice;
import com.statestreet.referencerate.ReferenceRateCalculator;

public class ReferencePriceCalculator implements ReferenceRateCalculator {

    private MarketConfiguration configuration = null;

    public void onConfiguration(Configuration configuration) {
        this.configuration = new MarketConfiguration(configuration);
    }

    public void onFxPrice(FxPrice fxPrice) {
        configuration.update(fxPrice);
    }

    public FxPrice calculate() {
        return configuration.getReferencePrice();
    }
}
