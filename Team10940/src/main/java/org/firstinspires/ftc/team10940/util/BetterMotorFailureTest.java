package org.firstinspires.ftc.team10940.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<Double> differeces1 = new ArrayList<>();
    private ArrayList<Double> differences2 = new ArrayList<>();

    //private x

    public BetterMotorFailureTest(MotorFailure mf) {
        this.mf = mf;

        runtime = new ElapsedTime();
    }

    public void update(float robotMovement1, float robotMovement2, float encoder1, float encoder2) {
        if(motor1Data.size() > 30) {
            if(calculateSum(motor1Data) > 0.2 && calculateSum(getDiffereceList(encoder1Data)) > 100) {
                mf.onFail(1);
            }

            if(calculateSum(motor2Data) > 0.2 && calculateSum(getDiffereceList(encoder2Data)) > 100) {
                mf.onFail(2);
            }


            motor1Data.clear();
            motor2Data.clear();

            encoder1Data.clear();
            encoder2Data.clear();
        }

        motor1Data.add((double) Math.abs(robotMovement1));
        motor2Data.add((double) Math.abs(robotMovement2));

        encoder1Data.add((double) encoder1);
        encoder2Data.add((double) encoder2);
    }

    private ArrayList<Double> getDiffereceList(List<Double> no) {
        ArrayList<Double> list = new ArrayList<>();

        if(!no.isEmpty()) {
            for(int i = 1; i < no.size() - 1; i++) {
                list.add(no.get(i) - no.get(i - 1));
            }
        }

        return list;
    }

    private double calculateSum(List<Double> marks) {
        Double sum = 0.0;

        if(!marks.isEmpty()) {
            for(Double mark : marks) {
                sum += mark;
            }
        }

        return sum;
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