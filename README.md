# StateStreetTestCode
Test Code for StateStreet
Reference rate calculator
 
The reference rate is the median of the mid prices for a list of configured markets. Stale rate should be discounted from the calculation. If all rates are stale, the reference rate should be stale too.
 
-          Market is essentially a combination of a PriceSource and PriceProvider, note that price provider is not defined (i.e. null) for some price sources.
-          There are approx. 50 different price sources and 30 different price providers.
-          The number of markets in the configuration varies from 3 to 15.
-          The rate gets recalculated after each market data update, thousands of ticks per second.
-          The configuration gets changed infrequently, 1 or 2 times per day.
-          There is a separate instance of ReferenceRateCalculator for each currency pair.
-          Every instance of ReferenceRateCalculator runs on a single thread, which means no synchronization required.
 
Please implement it in the way you would normally do in the real life at work. Keep it simple, reasonable and efficient. Add reasonable amount of unit tests too.
 
Here are the interfaces you are given:
 
public interface ReferenceRateCalculator {
    void onConfiguration(Configuration configuration);
 
    void onFxPrice(FxPrice fxPrice);
 
    FxPrice calculate();
 
    interface Configuration {
        int getSize();
 
        PriceSource getSource(int index);
 
        @Nullable
        PriceProvider getProvider(int index);
    }
}
 
public interface FxPrice {
    double getBid();
 
    double getOffer();
 
    boolean isStale();
 
    PriceSource getSource();
 
    @Nullable
    PriceProvider getProvider();
}
 
public enum PriceProvider {
    PROVIDER1,
    PROVIDER2,
    ...
    PROVIDER50
}
 
public enum PriceSource {
    SOURCE1,
    SOURCE2,
    ...
    SOURCE30
}
 
The life-cycle is as follows:
 
-          onConfiguration() is called to pass the list of markets we want to calculate the reference rate across.
-          onFxPrice() is called on each market data update. By calling getSource() and getProvider() you know there the price came from. The price can be stale, in which case bid and offer are not defined (may be NaN or something else).
-          Each onFxPrice() call is followed by calculate() call, which produces the reference rate. Bid and offer on the returning price are always set to the same value - the reference rate.
-          onConfiguration() may be called again, which will be followed by onFxPrice() and calculate() calls.
 
For the vast majority of Sources there is no Provider (i.e. it’s always null), and for a very few specific Sources there is a Provider (i.e. it’s never null) which makes the price feed from the given source “provider-specific”.
In other words, the provider is like an extension to the Source. So formally, the combination of Source and Provider is a unique identifier of a “market” (or “price feed”), even if Provider is null
