package com.confiance.common.enums;

public enum RecommendationType {
    POSITIONAL("Positional Trade - Short to medium term"),
    LONG_TERM("Long Term Investment"),
    MOMENTUM("Momentum Trade - Quick gains"),
    SWING("Swing Trade"),
    INTRADAY("Intraday Trade");

    private final String description;

    RecommendationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
