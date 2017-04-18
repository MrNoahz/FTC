package org.firstinspires.ftc.team10940.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noah on 4/4/17.
 */

public class MotorFailureTester {
    private MotorFailure mf;

    private ElapsedTime runtime;

    private ArrayList<Double> motor1Data = new ArrayList<>();
    private ArrayList<Double> motor2Data = new ArrayList<>();

    private ArrayList<Double> encoder1Data = new ArrayList<>();
    private ArrayList<Double> encoder2Data = new ArrayList<>();

    private double lastUpdate = 0;
    private boolean first = false;

    private boolean lastWasError1 = false;
    private boolean lastWasError2 = false;

    public MotorFailureTester(MotorFailure mf, ElapsedTime et) {
        this.mf = mf;

        runtime = et;
    }

    // Only suited for 2 motored robot with 2 encoders. Add more later. Proof of Concept
    public void update(float robotMovement1, float robotMovement2, float encoder1, float encoder2) {
        // Takes absolute value to make calculations simpler. Also we don't need to know the direction of the movement.

        motor1Data.add((double) Math.abs(robotMovement1));
        motor2Data.add((double) Math.abs(robotMovement2));

        encoder1Data.add((double) Math.abs(encoder1));
        encoder2Data.add((double) Math.abs(encoder2));

        if(!first) {
            first = true;
            lastUpdate = runtime.milliseconds();

            return;
        }

        if(runtime.milliseconds() - lastUpdate > 1000) { // If 1 second passed since last check
            double motor1Avg = calculateAverage(motor1Data);
            double motor2Avg = calculateAverage(motor2Data);

            double avgSlope1 = calculateAverageSlope(encoder1Data);
            double avgSlope2 = calculateAverageSlope(encoder2Data);

            if(motor1Avg > 0.1) {
                if(avgSlope1 < 300) { // Arbitrary low number to account for friction movement
                    if(lastWasError1)
                        mf.onFail(1);

                    lastWasError1 = true;
                }
            }

            if(motor2Avg > 0.1) {
                if(avgSlope2 < 300) { // Arbitrary low number to account for friction movement
                    if(lastWasError2)
                        mf.onFail(2);

                    lastWasError2 = true;
                }
            }

            motor1Data.clear();
            motor2Data.clear();

            encoder1Data.clear();
            encoder2Data.clear();

            lastUpdate = runtime.milliseconds();
        }

        //mf.onFail();
    }

    private double calculateAverageSlope(List<Double> list) {
        // Splits list into 2, gets the slope of each half, then averages that

        double pt1Slope = (list.get((int) Math.ceil(list.size() / 2)) - list.get(0)) / (int) Math.ceil(list.size() / 2);
        double pt2Slope = (list.get(list.size() - 1) - list.get((int) Math.ceil(list.size() / 2))) / (int) Math.ceil(list.size() / 2);

        return (pt1Slope + pt2Slope) / 2;
    }

    // Java 8 simplifies this within a single line but I would rather use Java 7 for compatibility
    private double calculateAverage(List<Double> marks) {
        Double sum = 0.0;

        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }

            return sum / marks.size();
        }

        return sum;
    }
}