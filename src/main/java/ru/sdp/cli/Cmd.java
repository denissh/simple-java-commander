package ru.sdp.cli;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Cmd {
    String name();

    String description() default "";
}
