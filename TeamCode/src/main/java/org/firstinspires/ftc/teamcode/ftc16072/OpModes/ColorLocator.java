package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.RotatedRect;

import java.util.List;
import java.util.Locale;

//@Autonomous
@SuppressWarnings("unused")
public class ColorLocator extends OpMode {

    public static final int CAMERA_WIDTH = 640;
    public static final int CAMERA_HEIGHT = 480;
    int CENTER_X = CAMERA_WIDTH/2;
    int CENTER_Y = CAMERA_HEIGHT/2;

    ColorBlobLocatorProcessor colorLocator;
    public void init(){
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
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();
    }

    public void loop (){
        ColorBlobLocatorProcessor.Blob blob = blobClosestToCenter();
        if(blob == null){
            telemetry.addData("Closest", "none");
        }
        else{
            double angle = blob.getBoxFit().angle;
            if (blob.getBoxFit().size.height > blob.getBoxFit().size.width){
                angle = 90 + angle;
            }
            telemetry.addData("Closest angle", angle);
        }
    }

    public ColorBlobLocatorProcessor.Blob blobClosestToCenter(){
        double closest = 100000000;
        ColorBlobLocatorProcessor.Blob returnBlob = null;

        List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();
        for(ColorBlobLocatorProcessor.Blob blob : blobs) {
            double distance = getDistanceFromCenter(blob.getBoxFit());
            if (distance < closest){
                closest = distance;
                returnBlob = blob;
            }
        }
        return returnBlob;
    }
    private double getDistanceFromCenter(RotatedRect boxFit){
        return  Math.abs(boxFit.center.x - CENTER_X) + Math.abs(boxFit.center.y - CENTER_Y);
    }
}
