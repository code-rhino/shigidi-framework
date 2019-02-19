package com.shigidi.bot.core.camera;

import com.shigidi.bot.core.camera.exceptions.CameraException;
import org.opencv.core.Mat;

public interface Camera {

    Mat getFrame() throws CameraException;
}
