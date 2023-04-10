package market.currency;

import market.model.Wallet;

public class CurrencyConverter {
    public static final double RUBBLE_DOLLAR_EXCHANGE_RATE = 70.00f;
    public static final double RUBBLE_EURO_EXCHANGE_RATE = 85.00f;

    public static double convert(Wallet from, double value, Wallet to) {
        if (from == to) {
            return value;
        }

        double valueInRubbles = value;
        if (from == Wallet.USD) {
            valueInRubbles = value * RUBBLE_DOLLAR_EXCHANGE_RATE;
        } else if (from == Wallet.EUR) {
            valueInRubbles = value * RUBBLE_EURO_EXCHANGE_RATE;
        }

        if (to == Wallet.USD) {
            return valueInRubbles / RUBBLE_DOLLAR_EXCHANGE_RATE;
        } else if (to == Wallet.EUR) {
            return valueInRubbles / RUBBLE_EURO_EXCHANGE_RATE;
        }

        return valueInRubbles;
    }
}
