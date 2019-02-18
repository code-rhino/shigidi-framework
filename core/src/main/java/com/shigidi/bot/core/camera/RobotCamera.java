package com.shigidi.bot.core.camera;

import com.shigidi.bot.core.server.HttpStreamServer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RobotCamera {

    public static Mat frame = null;
    private static HttpStreamServer httpStreamServer;
    static VideoCapture videoCapture;
    static Timer tmrVideoProcess;

    public static void start(){
        System.out.println("Starting");
        videoCapture = new VideoCapture();
        videoCapture.open(0);
        if(!videoCapture.isOpened()){
            return;
        }

        frame = new Mat();

        httpStreamServer = new HttpStreamServer(frame);
        new Thread(httpStreamServer).start();

        tmrVideoProcess = new Timer(100, new ActionListener() {

            public void actionPerformed(ActionEvent e){
                if (!videoCapture.read(frame)) {
                    tmrVideoProcess.stop();
                }
                Point point1 = new Point(0.0, 0.0);
                Point point2 = new Point(200, 200);
                Imgproc.rectangle(httpStreamServer.image,point1, point2,new Scalar(0, 255, 0, 255), 3);
                //procesed image
                httpStreamServer.image = frame;
            }
        });
        tmrVideoProcess.start();
    }
}
