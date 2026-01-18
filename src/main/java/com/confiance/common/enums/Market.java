package com.confiance.common.enums;

public enum Market {
    US("United States", "USD"),
    INDIA("India", "INR"),
    UK("United Kingdom", "GBP"),
    EU("European Union", "EUR"),
    SINGAPORE("Singapore", "SGD"),
    HONG_KONG("Hong Kong", "HKD"),
    JAPAN("Japan", "JPY"),
    CANADA("Canada", "CAD"),
    AUSTRALIA("Australia", "AUD");

    private final String displayName;
    private final String defaultCurrency;

    Market(String displayName, String defaultCurrency) {
        this.displayName = displayName;
        this.defaultCurrency = defaultCurrency;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }
}
