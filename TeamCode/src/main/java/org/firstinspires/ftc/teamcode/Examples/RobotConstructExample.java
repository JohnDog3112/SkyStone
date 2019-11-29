package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Functions.RobotConstructor;

import static org.firstinspires.ftc.teamcode.Functions.FunctionLibrary.GetYaw;


public class RobotConstructExample extends RobotConstructor {
    private static final String VuforiaKey = "";
    private static double wheelDiameter = 0;
    private static double dKp = 0;
    private static double minMoveSpeed = 0;
    private static float CameraForwardDisplacement = 0;
    private static float CameraLeftDisplacement = 0;
    private static float CameraVerticalDisplacement = 0;
    private static double rampingDistance = 12;



    public RobotConstructExample(LinearOpMode opMode) {
        super(opMode, wheelDiameter, dKp, minMoveSpeed,rampingDistance, CameraForwardDisplacement, CameraLeftDisplacement, CameraVerticalDisplacement, VuforiaKey);
    }
    @Override
    public void updateOdometry() {
        super.updateOdometry();
        addDeviation(new FunctionLibrary.Point(0,0));
    }

    @Override
    public void move(double x, double y, double rotation, double power) {

    }
}
