package com.sehoon.springgradlesample.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CmciVO extends CommonVO{
    private String userName;
}
