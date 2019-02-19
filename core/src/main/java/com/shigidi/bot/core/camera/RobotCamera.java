package com.shigidi.bot.core.camera;

import com.shigidi.bot.core.camera.exceptions.CameraException;
import com.shigidi.bot.core.server.HttpStreamServerImpl;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;

public class RobotCamera implements Camera{

    public static Mat frame = null;
    private static HttpStreamServerImpl httpStreamServerImpl;
    private static VideoCapture videoCapture;
    static Timer tmrVideoProcess;

    public RobotCamera(){
        videoCapture = new VideoCapture();
        videoCapture.open(0);
    }

    public static void start(){
        System.out.println("Starting");

        if(!videoCapture.isOpened()){
            return;
        }

        frame = new Mat();

        httpStreamServerImpl = new HttpStreamServerImpl(frame);
        new Thread(httpStreamServerImpl).start();

        tmrVideoProcess = new Timer(100, e->{
                if (!videoCapture.read(frame)) {
                    tmrVideoProcess.stop();
                }
                Point point1 = new Point(0.0, 0.0);
                Point point2 = new Point(200, 200);
                Imgproc.rectangle(httpStreamServerImpl.image,point1, point2,new Scalar(0, 255, 0, 255), 3);
                //procesed image
                httpStreamServerImpl.image = frame;
            }
        );
        tmrVideoProcess.start();
    }

    @Override
    public Mat getFrame() throws CameraException {
      //  System.out.println("Get Frame");
        Mat frame = new Mat();
        if (!videoCapture.read(frame)){
            throw new CameraException();
        }

        return frame;
    }
}
