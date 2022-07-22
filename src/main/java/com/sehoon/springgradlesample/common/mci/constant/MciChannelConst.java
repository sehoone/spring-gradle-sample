package com.sehoon.springgradlesample.common.mci.constant;

public enum MciChannelConst {
    CHANNAL_A("A"),
    CHANNAL_B("B");
    
    private final String value;

    MciChannelConst(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
