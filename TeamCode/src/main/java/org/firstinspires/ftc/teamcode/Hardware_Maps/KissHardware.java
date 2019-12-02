package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Functions.RobotConstructor;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class KissHardware extends RobotConstructor {
    private static final String VuforiaKey = "AUJrAPb/////AAAAGV6Dp0zFW0tbif2eZk4u4LsrIQNxlQdiTbA2UJgYbEh7rb+s+Gg9soHReFwRRQz9xAiUcZi6d4jtD9+keLWR9xwcT+zJFSfdajjl89kWcf99HIxpWIMuNfAKhW83arD48Jnz/MTYxuBajilzcUxcPYQx24G/MeA6ZlyBhEauLXCKVrsdddL9kaEatPQx1MblEiH5wbdsMsXHz7w0B9CyEhQyZRLXb0zSbijn+JhHaHblBEk40x7gxkQYM1F+f+GfTrx5xR7ibvldNjRJ0obz1NJfuZugfW4R4vpV3C8Qebk7Jmy4YdL62Kb8W2Xk/S55jDhsdNW8rCPvVGJqjM5useObvRhomu0UT5EDH6hwOYxU";
    private static final String Webcamname = "Webcam 1";
    private static double wheelDiameter = 4;
    private static double dKp = 0.05;
    private static double minMoveSpeed = 0.1;
    public static final float CameraForwardDisplacement = (float)6.5;
    public static final float CameraLeftDisplacement = (float)4;
    public static final float CameraVerticalDisplacement = (float)6.5;
    private static float rampingDistance = 12;

    public final double inchesPerTick;
    public final DcMotor dcFrontLeft;
    public final DcMotor dcFrontRight;
    public final DcMotor dcBackLeft;
    public final DcMotor dcBackRight;

    public final ColorSensor colorSensor;


    public KissHardware(LinearOpMode opMode) {
        super(opMode, wheelDiameter, dKp, minMoveSpeed,rampingDistance, CameraForwardDisplacement, CameraLeftDisplacement, CameraVerticalDisplacement, Webcamname, VuforiaKey);
        HardwareMap hMap = opMode.hardwareMap;
        dcFrontLeft = hMap.dcMotor.get("frontLeft");
        dcFrontRight = hMap.dcMotor.get("frontRight");
        dcBackLeft = hMap.dcMotor.get("backLeft");
        dcBackRight = hMap.dcMotor.get("backRight");

        colorSensor = hMap.colorSensor.get("color");

        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);

        dcFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        inchesPerTick = (1/dcFrontLeft.getMotorType().getTicksPerRev())*getWheelCircumfrance();
    }
    private double lastFrontLeftPos = 0;
    private double lastFrontRightPos = 0;
    private double lastBackLeftPos = 0;
    private double lastBackRightPos = 0;
    @Override
    public void updateOdometry() {
        super.updateOdometry();
        double frontLeftOffset = (dcFrontLeft.getCurrentPosition()- lastFrontLeftPos) * inchesPerTick;
        double frontRightOffset = (dcFrontRight.getCurrentPosition()- lastFrontRightPos) * inchesPerTick;
        double backLeftOffset = (dcBackLeft.getCurrentPosition()- lastBackLeftPos) * inchesPerTick;
        double backRightOffset = (dcBackRight.getCurrentPosition()- lastBackRightPos) * inchesPerTick;

        lastFrontLeftPos = dcFrontLeft.getCurrentPosition();
        lastFrontRightPos = dcFrontRight.getCurrentPosition();
        lastBackLeftPos = dcBackLeft.getCurrentPosition();
        lastBackRightPos = dcBackRight.getCurrentPosition();

        double yOffset = (frontLeftOffset+frontRightOffset+backLeftOffset+backRightOffset)/4;
        double xOffset = -(-frontLeftOffset + frontRightOffset + backLeftOffset - backRightOffset)/4;

        double hypot = sqrt(pow(xOffset,2) + pow(yOffset,2));

        double angle = Math.toDegrees(atan2(yOffset, xOffset));
        double adjustedAngle = angle - getWorldRotation();

        double deltaX = hypot*cos(Math.toRadians(adjustedAngle));
        double deltaY = hypot*sin(Math.toRadians(adjustedAngle));

        addDeviation(new FunctionLibrary.Point(deltaX, deltaY));
    }

    @Override
    public void move(double x, double y, double rotation, double power) {
        x = -x;
        double pFrontLeft= x + y;
        double pFrontRight = x - y;
        double pBackLeft= x - y;
        double pBackRight = x + y;

        //+--+

        double max = max(max(abs(pFrontRight),abs(pFrontLeft)),max(abs(pBackRight), abs(pBackRight)));
        if (max > power) {
            double scaler = power/max;
            pFrontLeft = pFrontLeft * scaler;
            pFrontRight = pFrontRight * scaler;
            pBackLeft = pBackLeft * scaler;
            pBackRight = pBackRight * scaler;
        }
        pFrontLeft = pFrontLeft + rotation;
        pFrontRight = pFrontRight - rotation;
        pBackLeft = pBackLeft + rotation;
        pBackRight = pBackRight - rotation;

        max = max(max(abs(pFrontRight),abs(pFrontLeft)),max(abs(pBackRight), abs(pBackRight)));
        if (max > power) {
            double scaler = power/max;
            pFrontLeft = pFrontLeft * scaler;
            pFrontRight = pFrontRight * scaler;
            pBackLeft = pBackLeft * scaler;
            pBackRight = pBackRight * scaler;
        }

        dcFrontLeft.setPower(pFrontLeft);
        dcFrontRight.setPower(pFrontRight);
        dcBackLeft.setPower(pBackLeft);
        dcBackRight.setPower(pBackRight);


        if (dcFrontLeft.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER) {

            dcFrontLeft.setTargetPosition((int)(x+y));
            dcFrontRight.setTargetPosition((int)(x-y));
            dcBackLeft.setTargetPosition((int)(x-y));
            dcBackRight.setTargetPosition((int)(x+y));
        }
    }
    @Override
    public DcMotor[] getDriveMotors() {
        DcMotor[] motors = new DcMotor[4];
        motors[0] = dcFrontLeft;
        motors[1] = dcFrontRight;
        motors[2] = dcBackLeft;
        motors[3] = dcBackRight;
        return motors;
    }
}
