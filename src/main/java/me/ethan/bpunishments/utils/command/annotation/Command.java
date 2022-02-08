package me.ethan.bpunishments.utils.command.annotation;

import org.bukkit.ChatColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author brickoh
 * @Project Nebula
 * @Date 25/07/2021 03:09
 * @GitHub https://github.com/brickoh
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name();

    String permission() default "";

    String noPermission() default "You do not have permission to perform that action.";

    String[] aliases() default {};

    String description() default "";

    String usage() default "";

    boolean inGameOnly() default false;
}