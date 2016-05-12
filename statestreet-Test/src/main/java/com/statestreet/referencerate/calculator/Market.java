package com.statestreet.referencerate.calculator;


import com.statestreet.referencerate.FxPrice;
import com.statestreet.referencerate.PriceProvider;
import com.statestreet.referencerate.PriceSource;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;

class Market {
    private final PriceSource source;
    private final PriceProvider provider;

    private boolean stale = true;
    private double max = MIN_VALUE;
    private double min = MAX_VALUE;
    private double mid = 0D;

    Market(PriceSource source, PriceProvider provider) {
        this.source = source;
        this.provider = provider;
    }

    Market update(FxPrice price) {
        if (source != price.getSource() && provider != price.getProvider())
            return this;

        stale = stale || price.isStale();
        if (!price.isStale()) { // ignore stale prices
            max = Math.max(max, price.getOffer() + price.getBid())/2D;
            min = Math.min(min, price.getOffer() + price.getBid())/2D;
            mid = (max + min) / 2D; // calculate mid range value
        }

        return this;
    }

    double getMidPrice() {

        return mid;
    }

    boolean isStale() {
        return stale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Market market = (Market) o;

        if (source != market.source) return false;
        return provider == market.provider;

    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }
}
