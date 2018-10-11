package com.neulion.recyclerdiff.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-13
 * Time: 18:07
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DiffItem
{
}
