package com.daol.logbuffer._common.policy;

public interface Policy<T> {

    Boolean isSatisfiedBy(T target);
}