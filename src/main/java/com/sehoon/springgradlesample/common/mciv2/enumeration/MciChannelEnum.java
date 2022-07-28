package com.sehoon.springgradlesample.common.mciv2.enumeration;

public enum MciChannelEnum {
    MCI_INNER("mci-inner"),
    MCI_OUTER("mci-outer");
    
    private final String value;

    MciChannelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
