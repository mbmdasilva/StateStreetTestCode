package com.statestreet.referencerate.calculator;

import com.statestreet.referencerate.FxPrice;
import com.statestreet.referencerate.PriceProvider;
import com.statestreet.referencerate.PriceSource;

class ReferencePrice implements FxPrice {
    public static final ReferencePrice STALE_FX_PRICE = new ReferencePrice();

    private final double value;

    private ReferencePrice() {
        this(Double.NaN);
    }

    public ReferencePrice(double value) {
        this.value = value;
    }

    @Override
    public double getBid() {
        return value;
    }

    @Override
    public double getOffer() {
        return value;
    }

    @Override
    public boolean isStale() {
        return value == Double.NaN;
    }

    @Override
    public PriceSource getSource() {
        return null;
    }

    @Override
    public PriceProvider getProvider() {
        return null;
    }
}