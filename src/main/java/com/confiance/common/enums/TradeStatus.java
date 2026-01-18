package com.confiance.common.enums;

public enum TradeStatus {
    OPEN("Open - Position held"),
    CLOSED("Closed - Fully exited"),
    PARTIALLY_SOLD("Partially Sold - Partial exit");

    private final String description;

    TradeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
