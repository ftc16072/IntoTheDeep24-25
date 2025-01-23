package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoServos;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestWebcam;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

import java.util.Arrays;
import java.util.List;

@Config
public class IntakeClaw extends QQMechanism {
    public static final double MAX_SERVO_POS = 1;
    public static final int DEGREES_TO_SERVO = 300;
    public static double CLAW_CLOSE_POSITION = 1;
    public static double CLAW_OPEN_POSITION = 0.8;
    public static double WRIST_START_POSITION = 0.0;
    public static double WRIST_INTAKE_POSITION = 0.7;

    ElapsedTime openTimer = new ElapsedTime();
    ElapsedTime closedTimer = new ElapsedTime();
    Servo clawServo;
    Servo leftWristServo;
    Servo rightWristServo;
    WebcamName webcamName;

    ColorBlobLocatorProcessor.Blob blob;
    double blobAngle;
    double CLOSED_TIME = 0.5;
    double OPEN_TIME = 0.5;
    double leftWristServoPos;
    double rightWristServoPos;

    public static final int CAMERA_WIDTH = 320;
    public static final int CAMERA_HEIGHT = 240;
    int CENTER_X = CAMERA_WIDTH / 2;
    int CENTER_Y = CAMERA_HEIGHT / 2;
    boolean visionIsActive = true;

    Point centerPoint = new Point(CAMERA_HEIGHT / 2.0, CAMERA_WIDTH / 2.0);

    ColorBlobLocatorProcessor colorLocator;


    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "intake_claw_servo");
        leftWristServo = hwMap.get(Servo.class, "left_intake_wrist");
        rightWristServo = hwMap.get(Servo.class,"right_intake_wrist");
        webcamName = hwMap.get(WebcamName.class, "Webcam 1");
        leftWristServo.setDirection(Servo.Direction.REVERSE);

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

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo),
                new TestTwoServos("wrist_movement", WRIST_START_POSITION, WRIST_INTAKE_POSITION, leftWristServo,rightWristServo),
                new TestWebcam("Webcam", webcamName)
        );
    }
    public void wristTransfer(){
        leftWristServoPos = WRIST_START_POSITION;
        rightWristServoPos = WRIST_START_POSITION;
    }
    public void wristIntake(){
        leftWristServoPos = WRIST_INTAKE_POSITION;
        rightWristServoPos = WRIST_INTAKE_POSITION;
    }
    public void open() {
        openTimer.reset();
        clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public void close() {
        closedTimer.reset();
        clawServo.setPosition(CLAW_CLOSE_POSITION);
    }

    public void adjustWrist(double manualChangeAmount){
        if (leftWristServoPos <= MAX_SERVO_POS && rightWristServoPos <= MAX_SERVO_POS){
            leftWristServoPos += manualChangeAmount;
            rightWristServoPos -= manualChangeAmount;
        }
    }    public boolean isClawOpen() {
        if ((clawServo.getPosition() == CLAW_OPEN_POSITION) &&
                (openTimer.seconds()> OPEN_TIME))
            return true;

        return false;
    }
    public boolean isClawClosed() {
        if ((clawServo.getPosition() == CLAW_CLOSE_POSITION) &&
                (closedTimer.seconds()> CLOSED_TIME))
            return true;

        return false;
    }


    @Override
    public void update(Telemetry telemetry){
        rightWristServo.setPosition(rightWristServoPos);
        leftWristServo.setPosition(leftWristServoPos);
        if (visionIsActive){
            blob = blobClosestToCenter();
            if(blob != null) {
                blobAngle = blob.getBoxFit().angle - 90;
                if (blob.getBoxFit().size.height > blob.getBoxFit().size.width) {
                    blobAngle = 90 + blobAngle;
                }
                telemetry.addData("Blob angle", blobAngle);
                telemetry.addData("Box center", blob.getBoxFit().center);
                telemetry.addData("has target", hasTarget());

            }
        }
    }

    double degreesToServoPos(double degrees){
        return degrees / DEGREES_TO_SERVO;
    }

    public void intakeDegrees(double degrees){
        double servoPosAdjustment = degreesToServoPos(degrees);
        leftWristServoPos = WRIST_INTAKE_POSITION - servoPosAdjustment;
        rightWristServoPos = WRIST_INTAKE_POSITION + servoPosAdjustment;
    }

    public void IntakeWithVision(){
        intakeDegrees(blobAngle);
    }

    public void startVision(){
        visionIsActive = true;
    }
    public void stopVision(){
        visionIsActive = false;
    }

    public boolean hasTarget() {
        if (blob != null){
            if (blob.getBoxFit().boundingRect().contains(centerPoint)) {
                return true;
            }
        }return false;
    }

}

