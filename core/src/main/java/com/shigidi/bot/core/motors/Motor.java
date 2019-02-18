package com.shigidi.bot.core.motors;

public interface Motor {
    void setMotorPower(int powerLevel);
    int getMotorPower();
    MotorSlot getMotorSlot();
}
