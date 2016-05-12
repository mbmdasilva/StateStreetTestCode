package com.statestreet.referencerate;


import com.statestreet.referencerate.FxPrice;
import com.statestreet.referencerate.PriceProvider;
import com.statestreet.referencerate.PriceSource;
import com.sun.istack.internal.Nullable;

import java.util.Map;

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