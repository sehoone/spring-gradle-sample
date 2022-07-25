package com.sehoon.springgradlesample.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SpecifyDataVO{
    private String cno;
    private String userName;
    private String userPhone;
    private int age;
}
