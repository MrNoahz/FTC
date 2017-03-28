package org.firstinspires.ftc.teamresearch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Vuforia", group="Research")

public class AutoVuforia extends LinearOpMode {
    private TestHardware robot   = new TestHardware();
    private ElapsedTime runtime = new ElapsedTime();

    private final String licenseKey = "AYhHjvv/////AAAAGf/j6w84N0tGkw7UwOOqEWFYxjWXrKaUkoR/1IFMFHMxLyiCkK7u4WwOSADKT0FiZveW1aE31n/35gAHg5pnZl7m7npW6oRXZe3tPoYHiHBqC29/oBnfpZaU2QhubIM5LCYj7rNoESJP4ivAS4ElubfItVHxlP3vl0xDErrPkO5N37e4gFEj5riMPCzT7t6aitMYj7Dx3USWjIeLR1BDKGnw4GMtQf3//gid9+D97gB/MHy/P+QbrhF65XCeTfsMiJPWQNfZAQiNA2kUCwwONbUZKovgdKg4McX/OXIkA3eRLAdpyBpyhhyZprlIsSSlCUY5CEJ5RPsLfADSbhmYARTaOapVIv4hz4FvcY3WoGMf ";

    private final float mmPerInch  = 25.4f;
    private final float botWidth   = 18 * mmPerInch;
    private final float fieldWidth = (12 * 12 - 2) * mmPerInch; // the FTC field is ~11'10" center-to-center of the glass panels

    @Override
    public void runOpMode() throws InterruptedException {
        //robot.init(hardwareMap);

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection   = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = licenseKey;
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.BUILDINGS;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        waitForStart();

        runtime.reset();
        beacons.activate();

        while(opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            for(VuforiaTrackable beacon : beacons) {
                OpenGLMatrix pos = ((VuforiaTrackableDefaultListener) beacon.getListener()).getPose();

                if (pos != null) {
                    VectorF translation = pos.getTranslation();

                    telemetry.addData(beacon.getName() + " Translation:", translation);

                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2))); //If phone is landscape, translation.get(0) and translation.get(2)

                    telemetry.addData(beacon.getName() + " Degrees", degreesToTurn);
                }
            }

            telemetry.update();
        }
    }
}