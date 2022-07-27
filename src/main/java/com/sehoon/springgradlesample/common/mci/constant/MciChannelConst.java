package com.sehoon.springgradlesample.common.mci.constant;

public enum MciChannelConst {
    NL_CDP("nl-cdp"),
    MCI_OUTER("mci-outer");
    
    private final String value;

    MciChannelConst(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
