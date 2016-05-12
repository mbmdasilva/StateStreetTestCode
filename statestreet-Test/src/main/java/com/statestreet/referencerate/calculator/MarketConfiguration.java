package com.statestreet.referencerate.calculator;


import com.statestreet.referencerate.FxPrice;
import com.statestreet.referencerate.PriceProvider;
import com.statestreet.referencerate.PriceSource;
import com.statestreet.referencerate.ReferenceRateCalculator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.statestreet.referencerate.calculator.ReferencePrice.STALE_FX_PRICE;
import static java.lang.Math.abs;
import static java.lang.System.getProperty;
import static java.util.Collections.EMPTY_MAP;

class MarketConfiguration {
    public static final double PRECISION = Double.valueOf(getProperty("MarketConfiguration.precision", "0.0001"));

    private Set<Market> activeMarkets = new HashSet<>();
    private Map<PriceSource, Map<PriceProvider, Market>> allMarkets = new HashMap<>();

    private double medianPrice;
    private double meanPrice;

    // If all rates are stale, the reference rate should be stale too.
    private ReferencePrice referencePrice = STALE_FX_PRICE;

    MarketConfiguration(ReferenceRateCalculator.Configuration configuration) {
        for (int i = 0; i < configuration.getSize(); i++) {
            PriceSource source = configuration.getSource(i);
            PriceProvider provider = configuration.getProvider(i);

            addMarket(source, provider);
        }
    }

    private void addMarket(PriceSource source, PriceProvider provider) {
        Map<PriceProvider, Market> providers = allMarkets.getOrDefault(source, new HashMap());
        Market market = providers.getOrDefault(provider, new Market(source, provider));
        providers.put(provider, market);
        allMarkets.put(source, providers);
    }

    public MarketConfiguration update(FxPrice price) {
        Market market = (Market) allMarkets.getOrDefault(price.getSource(), EMPTY_MAP).get(price.getProvider());
        if (market == null) {
            //TODO: needs clarification.
            // this is a price for a market that was not in the configuration. I guess we ignore it!.
            return this;
        }

        double previous = market.getMidPrice();
        double current = market.update(price).getMidPrice();


        if (!market.isStale() && abs(previous - current) < PRECISION) {


            medianPrice = medianPrice * activeMarkets.size() - abs(previous - meanPrice);
            meanPrice = meanPrice * activeMarkets.size() - previous;

            // this potentially may add a new market, so changing the size of the active markets
            activeMarkets.add(market);

            meanPrice = (meanPrice + current) / activeMarkets.size();
            medianPrice = (meanPrice + abs(current - meanPrice)) / activeMarkets.size();

            referencePrice = new ReferencePrice(medianPrice);
        }

        return this;
    }

    public FxPrice getReferencePrice() {
        return referencePrice;
    }

}