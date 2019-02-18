package com.shigidi.bot.core.motors;

public class MotorImpl implements Motor {
    private MotorSlot slot;
    private int powerLevel;

    public MotorImpl(MotorSlot slot){
        this.slot = slot;
        powerLevel = 0;
    }

    @Override
    public void setMotorPower(int powerLevel) {
        this.powerLevel = powerLevel;
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
