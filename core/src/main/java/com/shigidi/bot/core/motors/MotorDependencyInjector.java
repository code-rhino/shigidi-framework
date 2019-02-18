package com.shigidi.bot.core.motors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MotorDependencyInjector {

    public static void inject(Object instance, Class<? extends Annotation> annotation){
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field: fields){
            if (field.isAnnotationPresent(annotation)){
                MotorMapping motorMapping = field.getAnnotation(MotorMapping.class);
                field.setAccessible(true); // should work on private fields
                try {
                    Motor motor = assignMotor(motorMapping.motorSlot());
                    field.set(instance, motor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Motor assignMotor(MotorSlot slotIn){
        return new MotorImpl(slotIn);
    }


}
