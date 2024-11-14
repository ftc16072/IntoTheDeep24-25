package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestWebcam;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.RotatedRect;

import java.util.Arrays;
import java.util.List;

@Config
public class IntakeClawVision extends QQMechanism {
    public static double CLAW_CLOSE_POSITION = 0.3;
    public static double CLAW_OPEN_POSITION = 0;
    public static double WRIST_START_POSITION = 0;
    public static double WRIST_END_POSITION = 0.67;

    Servo clawServo;
    Servo wristServo;
    WebcamName webcamName;
    public Telemetry telemetry;

    public static final int CAMERA_WIDTH = 640;
    public static final int CAMERA_HEIGHT = 480;
    int CENTER_X = CAMERA_WIDTH / 2;
    int CENTER_Y = CAMERA_HEIGHT / 2;

    ColorBlobLocatorProcessor colorLocator;

    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "intake_claw_servo");
        wristServo = hwMap.get(Servo.class, "intake_wrist_servo");
        webcamName = hwMap.get(WebcamName.class, "Webcam 1");

        colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(ColorRange.RED)         // use a predefined color match
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1, 1, 1, -1))  // search central 1/4 of camera view
                .setDrawContours(true)                        // Show contours on the Stream Preview
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();
        @SuppressWarnings("unused")
        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(CAMERA_WIDTH, CAMERA_HEIGHT))
                .setCamera(webcamName)
                .build();
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo),
                new TestServo("wrist_movement", WRIST_START_POSITION, WRIST_END_POSITION, wristServo),
                new TestWebcam("Webcam", webcamName)
        );
    }

    @Override
    public void update() {
        ColorBlobLocatorProcessor.Blob blob = blobClosestToCenter();
        if (blob != null) {
            double angle = blob.getBoxFit().angle;
            if (blob.getBoxFit().size.height > blob.getBoxFit().size.width) {
                angle = 90 + angle;
            }
            setWristAngle(angle);
        }
    }

    public ColorBlobLocatorProcessor.Blob blobClosestToCenter() {
        double closest = 100000000;
        ColorBlobLocatorProcessor.Blob returnBlob = null;

        List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();
        for (ColorBlobLocatorProcessor.Blob blob : blobs) {
            double distance = getDistanceFromCenter(blob.getBoxFit());
            if ((blob.getContourArea() > 50) && (distance < closest)) {
                closest = distance;
                returnBlob = blob;
            }
        }
        return returnBlob;
    }

    private double getDistanceFromCenter(RotatedRect boxFit) {
        return Math.abs(boxFit.center.x - CENTER_X) + Math.abs(boxFit.center.y - CENTER_Y);
    }

    public void open() {
        clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public void close() {
        clawServo.setPosition(CLAW_CLOSE_POSITION);
    }
    public void setWristAngle(double angle){
        double servoAngle = WRIST_START_POSITION + (WRIST_END_POSITION - WRIST_START_POSITION) * (angle/180);
        if(telemetry != null){
            telemetry.addData("angle", angle);
            telemetry.addData("ServoAngle", servoAngle);
        }
        wristServo.setPosition(servoAngle);
    }
}

