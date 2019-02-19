package com.shigidi.bot.core.motors;

import com.shigidi.bot.core.motors.annotations.MotorMapping;
import com.shigidi.bot.core.motors.interfaces.Motor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MotorDI {

    public static void inject(Object instance, Class<? extends Annotation> annotation){
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field: fields){
            if (field.isAnnotationPresent(annotation)){
                MotorMapping motorMapping = (MotorMapping) field.getAnnotation(annotation);
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
