package com.shigidi.bot.core.camera;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class CameraDependencyInjector {

    public static void inject(Object instance, Class<? extends Annotation> annotation){
        Field[] fields = instance.getClass().getDeclaredFields();
        System.out.println("inject");
        for (Field field: fields){
            if (field.isAnnotationPresent(annotation)){
                System.out.println("Found Camera");
                field.setAccessible(true); // should work on private fields
                try {
                    Camera camera = new RobotCamera();
                    field.set(instance, camera);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
