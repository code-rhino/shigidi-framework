package com.shigidi.bot.core.server;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpStreamServerImpl implements HttpStreamServer, Runnable{
    private ServerSocket serverSocket;
    private Socket socket;
    private final String boundary = "stream";
    private OutputStream outputStream;
    public Mat image;

    public HttpStreamServerImpl(Mat imageFrame){
        this.image = imageFrame;
    }

    public HttpStreamServerImpl() {
        this.image = new Mat();
    }

    public void startStreamingServer() throws IOException {
        System.out.println("Starting Streaming server");
        serverSocket = new ServerSocket(8081);
        socket = serverSocket.accept();
        writeHeader(socket.getOutputStream(), boundary);
    }

    private void writeHeader(OutputStream stream, String boundary) throws IOException {
        stream.write(("HTTP/1.0 200 OK\r\n" +
                "Connection: close\r\n" +
                "Max-Age: 0\r\n" +
                "Expires: 0\r\n" +
                "Cache-Control: no-store, no-cache, must-revalidate, pre-check=0, post-check=0, max-age=0\r\n" +
                "Pragma: no-cache\r\n" +
                "Content-Type: multipart/x-mixed-replace; " +
                "boundary=" + boundary + "\r\n" +
                "\r\n" +
                "--" + boundary + "\r\n").getBytes());

    }

    public void pushImage(Mat frame) throws IOException {
        if (frame == null)
            return;

        try {
            outputStream = socket.getOutputStream();
            BufferedImage img = Mat2bufferedImage(frame);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();
            outputStream.write(("Content-type: image/jpeg\r\n" +
                    "Content-Length: " + imageBytes.length + "\r\n" +
                    "\r\n").getBytes());
            outputStream.write(imageBytes);
            outputStream.write(("\r\n--" + boundary + "\r\n").getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            socket = serverSocket.accept();
            writeHeader(socket.getOutputStream(), boundary);
        }
    }

    public void run() {
        try {
            System.out.println("Yo yo  http://localhost:8080 with browser");
            startStreamingServer();
            System.out.println("Hello world");
            while (true) {
                System.out.println("Pushing Image sir");
                pushImage(image);
            }
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
            return;
        }

    }

    public void updateImage(Mat frame){
        this.image = frame;
    }

    public void stopStreamingServer() throws IOException {
        socket.close();
        serverSocket.close();
    }

    public static BufferedImage Mat2bufferedImage(Mat image) throws IOException {
        MatOfByte bytemat = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage img = null;
        img = ImageIO.read(in);
        return img;
    }
}
