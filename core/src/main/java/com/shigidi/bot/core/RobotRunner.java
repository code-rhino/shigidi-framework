package com.shigidi.bot.core;

import com.shigidi.bot.core.camera.annotations.CameraMapping;
import com.shigidi.bot.core.motors.annotations.MotorMapping;
import com.shigidi.bot.core.robot.Robot;
import com.shigidi.bot.core.motors.MotorDI;
import com.shigidi.bot.core.camera.CameraDependencyInjector;
import com.shigidi.bot.core.server.HttpStreamServerDI;
import com.shigidi.bot.core.server.HttpStreamServerImpl;
import org.opencv.core.Core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RobotRunner {


    public static void initialize(Class<?> clazz) {
        System.out.println("Run robot initialize " + clazz.getTypeName());
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //RobotCamera.start();
        try {
            for (Method method : clazz.getDeclaredMethods()) {
                System.out.println(method.getName());
            }
            Object object = null;
            for(Constructor<?> ctor : clazz.getConstructors()){
                Class<?>[] paramTypes = ctor.getParameterTypes();
                if ( 0 == paramTypes.length){
                    Object[] convertedArgs = new Object[0];
                    object = ctor.newInstance(convertedArgs);
                    MotorDI.inject(object, MotorMapping.class);
                    //CameraDependencyInjector.inject(object, CameraMapping.class);
                    //HttpStreamServerImpl server = HttpStreamServerDI.inject(object);
                    //new Thread(server).start();
                    Robot robot = (Robot) object;
                    robot.run();
                }
            }
        }  catch (Exception ex){
            System.out.println(ex);
        }

    }

    private void injectMotors(){

    }
}
