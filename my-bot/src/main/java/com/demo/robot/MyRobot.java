package com.demo.robot;

import com.shigidi.bot.core.annotations.RobotInfo;
import com.shigidi.bot.core.RobotRunner;
import com.shigidi.bot.core.motors.MotorMapping;
import com.shigidi.bot.core.motors.Motor;
import com.shigidi.bot.core.motors.MotorSlot;
import com.shigidi.bot.core.robot.Robot;


@RobotInfo(
        createdBy = "Code Rhino",
        robotName = "Optimus Prime"
)
public class MyRobot implements Robot {

    @MotorMapping(motorSlot = MotorSlot.A)
    private Motor motorA;

    @MotorMapping(motorSlot = MotorSlot.B)
    private Motor motorB;

    public static void main(String[] args){
        RobotRunner.initialize(MyRobot.class);
    }

    @Override
    public void run() {
        motorA.setMotorPower(10);
        motorB.setMotorPower(-10);
        System.out.println(motorA.getMotorSlot() + "  " + motorA.getMotorPower());
        System.out.println(motorB.getMotorSlot() + "  " + motorB.getMotorPower());
        motorB.setMotorPower(8);
        System.out.println(motorB.getMotorSlot() + "  " + motorB.getMotorPower());

    }
}
