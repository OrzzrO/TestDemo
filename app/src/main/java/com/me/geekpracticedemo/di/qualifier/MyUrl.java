package com.me.geekpracticedemo.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by user on 2017/7/19.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyUrl {
}
