package com.sehoon.springgradlesample.common.mci.constant;

public enum MciChannelConst {
    MCI_INNER("mci-inner"),
    MCI_OUTER("mci-outer");
    
    private final String value;

    MciChannelConst(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
