package com.sehoon.springgradlesample.common.mci.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MciHeaderVO{
    private String tgrmLencn = "00000000";
    private String glbId;
    private String pgrsSriaNo;
    private String trgmVrsnInfoValu = "001";

}
