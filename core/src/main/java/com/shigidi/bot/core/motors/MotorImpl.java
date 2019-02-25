package com.shigidi.bot.core.motors;

import com.pi4j.io.i2c.I2CDevice;
import com.shigidi.bot.core.motors.interfaces.Motor;

import java.io.IOException;

public class MotorImpl implements Motor {
    private MotorSlot slot;
    private int powerLevel;
    private I2CDevice thunderborg;

    public MotorImpl(MotorSlot slot, I2CDevice thunderborg){
        this.slot = slot;
        this.thunderborg = thunderborg;
        powerLevel = 0;
    }

    @Override
    public void setMotorPower(int powerLevel) {
        System.out.println("This is motor " + slot.name());
        if(thunderborg != null){
            if(slot.equals(MotorSlot.A))
                writeToBoard(Constants.COMMAND_SET_A_FWD, powerLevel);
            else
                writeToBoard(Constants.COMMAND_SET_B_FWD, powerLevel);
        }
        this.powerLevel = powerLevel;
    }

    private void writeToBoard(int constant, int powerLevel){
        try {
            if (powerLevel > 0){
                thunderborg.write(constant, (byte) Constants.PWM_MAX);
            }else {
                thunderborg.write(constant, (byte) 0);
            }
        } catch (IOException ex){
            System.out.println("could not write to board");
        }
    }

    @Override
    public int getMotorPower() {
        return powerLevel;
    }

    @Override
    public MotorSlot getMotorSlot() {
        return slot;
    }
}
