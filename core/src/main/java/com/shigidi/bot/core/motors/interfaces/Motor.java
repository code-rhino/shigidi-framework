package com.shigidi.bot.core.motors.interfaces;

import com.shigidi.bot.core.motors.MotorSlot;

public interface Motor {
    void setMotorPower(int powerLevel);
    int getMotorPower();
    MotorSlot getMotorSlot();
}
