package me.ethan.bpunishments.utils.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author brickoh
 * @Project Nebula
 * @Date 25/07/2021 03:12
 * @GitHub https://github.com/brickoh
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Completer {

    String name();

    String[] aliases() default {};

}