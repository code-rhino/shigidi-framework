package com.shigidi.bot.core.motors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.shigidi.bot.core.motors.annotations.MotorMapping;
import com.shigidi.bot.core.motors.interfaces.Motor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MotorDI {

    public static void inject(Object instance, Class<? extends Annotation> annotation){

        I2CDevice thunderborg = connectToBoard();
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field: fields){
            if (field.isAnnotationPresent(annotation)){
                MotorMapping motorMapping = (MotorMapping) field.getAnnotation(annotation);
                field.setAccessible(true); // should work on private fields
                try {
                    Motor motor = assignMotor(motorMapping.motorSlot(), thunderborg);
                    field.set(instance, motor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static I2CDevice connectToBoard(){
        I2CBus I2C_BUS;
        I2CDevice Thunderborg;
        try {
            I2C_BUS = I2CFactory.getInstance(I2CBus.BUS_1);
            Thunderborg = I2C_BUS.getDevice(Constants.I2C_ID_THUNDERBORG);
            System.out.println("Device id " + Thunderborg.read(Constants.COMMAND_GET_ID));
            return Thunderborg;

        }catch(IOException ex){
            System.out.println("No Board");
        }
        return null;
    }
    private static Motor assignMotor(MotorSlot slotIn, I2CDevice board){
        return new MotorImpl(slotIn, board);
    }


}
