package com.confiance.common.enums;

public enum Salutation {
    MR("Mr."),
    MRS("Mrs."),
    MS("Ms."),
    DR("Dr."),
    PROF("Prof.");

    private final String displayName;

    Salutation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
