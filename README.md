# shigidi-framework

cmake -D CMAKE_BUILD_TYPE=RELEASE -D WITH_OPENCL=OFF -D BUILD_PERF_TESTS=OFF -D BUILD_SHARED_LIBS=OFF -D JAVA_INCLUDE_PATH=$JAVA_HOME/include -D JAVA_AWT_LIBRARY=$JAVA_HOME/jre/lib/amd64/libawt.so -D JAVA_JVM_LIBRARY=$JAVA_HOME/jre/lib/arm/server/libjvm.so -D CMAKE_INSTALL_PREFIX=/usr/local -D WITH_FFMPEG=OFF  ..

java -Djava.library.path=/home/pi/opencv/build/lib -cp .:/home/pi/opencv/build/bin/opencv-401.jar Test

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
 
public class Webcam {
 
    public static void main (String args[]) {
        System.out.println("Hello, OpenCV");
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.NATIVE_LIBRARY_NAME.toString());
 
        VideoCapture camera = new VideoCapture(0);
        try 
        {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!camera.isOpened()){
            System.out.println("Camera Error");
        }
        else{
            System.out.println("Camera OK?");
        }
 
        Mat frame = new Mat();
        camera.read(frame);
        Highgui.imwrite("capture.jpg", frame);
    }
}
