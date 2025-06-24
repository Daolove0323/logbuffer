package com.daol.logbuffer._common.argresolver;

import com.daol.logbuffer.member.common.Grade;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    Grade value();
}