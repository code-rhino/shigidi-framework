package com.shigidi.bot.core.server;

import java.lang.reflect.Field;

public class HttpStreamServerDI {
    public static HttpStreamServerImpl inject(Object instance){
        HttpStreamServerImpl server = null;
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field: fields){
            if (field.isAnnotationPresent(HttpStreamServerMapping.class)){
                System.out.println("Found Server");
                field.setAccessible(true); // should work on private fields
                try {
                    server = new HttpStreamServerImpl();
                    field.set(instance, server);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return server;
    }
}
