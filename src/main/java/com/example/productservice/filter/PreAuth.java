package com.example.productservice.filter;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {

    String roles();

}
