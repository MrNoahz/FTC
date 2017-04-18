package org.firstinspires.ftc.team10940.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

/**
 * Created by noah on 4/13/17.
 */

public class BetterMotorFailureTest {
    private MotorFailure mf;

    private ElapsedTime runtime;

    private ArrayList<Double> motor1Data = new ArrayList<>();
    private ArrayList<Double> motor2Data = new ArrayList<>();

    private ArrayList<Double> encoder1Data = new ArrayList<>();
    private ArrayList<Double> encoder2Data = new ArrayList<>();

    //private x

    public BetterMotorFailureTest(MotorFailure mf, ElapsedTime et) {
        this.mf = mf;

        runtime = et;
    }

    public void update(float robotMovement1, float robotMovement2, float encoder1, float encoder2) {
        motor1Data.add((double) Math.abs(robotMovement1));
        motor2Data.add((double) Math.abs(robotMovement2));

        encoder1Data.add((double) encoder1);
        encoder2Data.add((double) encoder2);
    }
}