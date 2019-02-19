package com.shigidi.bot.core.server;

import org.opencv.core.Mat;


public interface HttpStreamServer {
    void updateImage(Mat frame);
}
