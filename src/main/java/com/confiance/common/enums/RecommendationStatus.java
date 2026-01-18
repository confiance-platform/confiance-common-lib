package com.confiance.common.enums;

public enum RecommendationStatus {
    OPEN("Active recommendation"),
    CLOSED("Target achieved or stopped out"),
    PARTIALLY_SOLD("Partial profit booked"),
    EXPIRED("Recommendation expired");

    private final String description;

    RecommendationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
