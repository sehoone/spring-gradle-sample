package com.sehoon.springgradlesample.common.mciv2.message.header;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MciDataField {
    int length();

    boolean optional() default false;

    boolean required() default false;

    boolean right() default false;

    String format() default "";
}
