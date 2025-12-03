package org.codeup.parknexus.service.strategy;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Strategy interface for fee calculation.
 */
public interface IFeeCalculationStrategy {
    /**
     * Calculate the fee for a parking session given its duration.
     *
     * @param duration parking duration (non-null)
     * @return calculated fee as BigDecimal (scale 2)
     */
    BigDecimal calculateFee(Duration duration);

    /**
     * Optional: name of the strategy for logging or selection.
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}

