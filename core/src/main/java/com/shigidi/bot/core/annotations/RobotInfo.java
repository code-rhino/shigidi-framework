package com.shigidi.bot.core.annotations;

public @interface RobotInfo {

    String[] tags() default "";

    String createdBy() default "Not Set";

    String robotName() default "Not Set";
}
