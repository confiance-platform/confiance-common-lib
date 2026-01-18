package com.confiance.common.enums;

public enum TradeType {
    BUY("Buy"),
    SELL("Sell");

    private final String displayName;

    TradeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
