package com.koniosoftworks.kvstreaming.presentation.di.scope;

import com.google.inject.ScopeAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by max on 5/20/17.
 */
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@ScopeAnnotation
public @interface PerSocket {
}
