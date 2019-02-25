package com.demo.robot;

import com.shigidi.bot.core.annotations.RobotInfo;
import com.shigidi.bot.core.RobotRunner;
import com.shigidi.bot.core.camera.Camera;
import com.shigidi.bot.core.camera.annotations.CameraMapping;
import com.shigidi.bot.core.camera.exceptions.CameraException;
import com.shigidi.bot.core.motors.annotations.MotorMapping;
import com.shigidi.bot.core.motors.interfaces.Motor;
import com.shigidi.bot.core.motors.MotorSlot;
import com.shigidi.bot.core.robot.Robot;
import com.shigidi.bot.core.server.HttpStreamServer;
import com.shigidi.bot.core.server.HttpStreamServerMapping;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;


@RobotInfo(
        createdBy = "Code Rhino",
        robotName = "Optimus Prime"
)
public class MyRobot implements Robot {

    @MotorMapping(motorSlot = MotorSlot.A)
    private Motor motorA;

    @MotorMapping(motorSlot = MotorSlot.B)
    private Motor motorB;

    @CameraMapping
    Camera camera;

    @HttpStreamServerMapping
    HttpStreamServer httpStreamServer;

    public static void main(String[] args){
        RobotRunner.initialize(MyRobot.class);
    }

    @Override
    public void run() {
        try {
            camera.getFrame();
            motorA.setMotorPower(10);
            Thread.sleep(10l);
            motorB.setMotorPower(10);
            Thread.sleep(2000l);
            motorA.setMotorPower(0);
            Thread.sleep(10l);
            motorB.setMotorPower(0);
            //motorB.setMotorPower(-10);
            //System.out.println(motorA.getMotorSlot() + "  " + motorA.getMotorPower());
            //System.out.println(motorB.getMotorSlot() + "  " + motorB.getMotorPower());
            //motorB.setMotorPower(8);
            //System.out.println(motorB.getMotorSlot() + "  " + motorB.getMotorPower());
            /*while(true){
                Mat frame = camera.getFrame();
                Point point1 = new Point(0.0, 0.0);
                Point point2 = new Point(200, 200);
                Imgproc.rectangle(frame,point1, point2,new Scalar(0, 255, 0, 255), 3);
                httpStreamServer.updateImage(frame);

            }*/
        } catch (CameraException ex){
            ex.printStackTrace();
        }catch (InterruptedException ex){

        }

    }
}
