package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Functions.RobotConstructor;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class D1V4hardware extends RobotConstructor {
    //setup initial parameters that are provided to the parent robotconstructor class
    private static final String VuforiaKey = "AUJrAPb/////AAAAGV6Dp0zFW0tbif2eZk4u4LsrIQNxlQdiTbA2UJgYbEh7rb+s+Gg9soHReFwRRQz9xAiUcZi6d4jtD9+keLWR9xwcT+zJFSfdajjl89kWcf99HIxpWIMuNfAKhW83arD48Jnz/MTYxuBajilzcUxcPYQx24G/MeA6ZlyBhEauLXCKVrsdddL9kaEatPQx1MblEiH5wbdsMsXHz7w0B9CyEhQyZRLXb0zSbijn+JhHaHblBEk40x7gxkQYM1F+f+GfTrx5xR7ibvldNjRJ0obz1NJfuZugfW4R4vpV3C8Qebk7Jmy4YdL62Kb8W2Xk/S55jDhsdNW8rCPvVGJqjM5useObvRhomu0UT5EDH6hwOYxU";
    private static final String Webcamname = "Webcam 1";
    private static double wheelDiameter = 4;
    private static double dKp = 0.05;
    private static double minMoveSpeed = 0.1;
    private static float CameraForwardDisplacement = (float)6.5;
    private static float CameraLeftDisplacement = 4;
    private static float CameraVerticalDisplacement = (float)6.5;
    private static float rampingDistance = 12;


    public final double inchesPerTick;

    //initialize the variables for the hardware devices
    public final DcMotor dcFrontLeft;
    public final DcMotor dcFrontRight;
    public final DcMotor dcBackLeft;
    public final DcMotor dcBackRight;
    public final DcMotor dcUpDown;
    public final DcMotor dcInOut;
    public final DcMotor dcOpenClose;

    public final CRServo csRight;
    public final CRServo csLeft;

    public final Servo sHook;

    public final TouchSensor upperLimitSwitch;
    public final TouchSensor lowerLimitSwitch;


    //setup the constructor function
    public D1V4hardware(LinearOpMode opMode) {
        //provide the opMode given on creation as well as the variables defined above
        super(opMode, wheelDiameter, dKp, minMoveSpeed,rampingDistance, CameraForwardDisplacement, CameraLeftDisplacement, CameraVerticalDisplacement, Webcamname, VuforiaKey);
        //save the hardware map from the opMode
        HardwareMap hMap = opMode.hardwareMap;
        //set the variables to their corresponding hardware device
        dcFrontLeft = hMap.dcMotor.get("frontleft");
        dcFrontRight = hMap.dcMotor.get("frontright");
        dcBackLeft = hMap.dcMotor.get("backleft");
        dcBackRight = hMap.dcMotor.get("backright");
        dcInOut = hMap.dcMotor.get("inout");
        dcOpenClose = hMap.dcMotor.get("openclose");
        dcUpDown = hMap.dcMotor.get("updown");
        csRight = hMap.crservo.get("sright");
        csLeft = hMap.crservo.get("sleft");
        sHook = hMap.servo.get("shook");

        upperLimitSwitch = hMap.touchSensor.get("upperlimit");
        lowerLimitSwitch = hMap.touchSensor.get("downlimit");

        //setup the directions the devices need to operate in
        dcFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        dcBackLeft.setDirection(DcMotor.Direction.REVERSE);
        dcUpDown.setDirection(DcMotor.Direction.REVERSE);

        //make sure none of the devices are running
        dcFrontLeft.setPower(0);
        dcFrontRight.setPower(0);
        dcBackLeft.setPower(0);
        dcBackRight.setPower(0);
        dcOpenClose.setPower(0);
        dcUpDown.setPower(0);
        dcInOut.setPower(0);

        csRight.setPower(0);
        csLeft.setPower(0);

        sHook.setPosition(0);
        //initialize a variable useful in the odometry function
        inchesPerTick = (1/dcFrontLeft.getMotorType().getTicksPerRev())*getWheelCircumfrance();
    }
    //intialize the last encoder positions of the drive motors
    private double lastFrontLeftPos = 0;
    private double lastFrontRightPos = 0;
    private double lastBackLeftPos = 0;
    private double lastBackRightPos = 0;
    //overide the odometry function to make it robot specific
    @Override
    public void updateOdometry() {
        //call the parent's updateOdometry class to update rotation
        super.updateOdometry();
        //find the offset from the current encoder positions to the previous ones
        double frontLeftOffset = (dcFrontLeft.getCurrentPosition()- lastFrontLeftPos) * inchesPerTick;
        double frontRightOffset = (dcFrontRight.getCurrentPosition()- lastFrontRightPos) * inchesPerTick;
        double backLeftOffset = (dcBackLeft.getCurrentPosition()- lastBackLeftPos) * inchesPerTick;
        double backRightOffset = (dcBackRight.getCurrentPosition()- lastBackRightPos) * inchesPerTick;

        //set the previous values to the current ones for next iteration
        lastFrontLeftPos = dcFrontLeft.getCurrentPosition();
        lastFrontRightPos = dcFrontRight.getCurrentPosition();
        lastBackLeftPos = dcBackLeft.getCurrentPosition();
        lastBackRightPos = dcBackRight.getCurrentPosition();

        //use inverse kinimatics to find the x and y offsets
        double yOffset = -(frontLeftOffset+frontRightOffset+backLeftOffset+backRightOffset)/4;
        double xOffset = (-frontLeftOffset + frontRightOffset + backLeftOffset - backRightOffset)/4;

        //find the straight line distance from the last position
        double hypot = sqrt(pow(xOffset,2) + pow(yOffset,2));

        //find the angle of movement
        double angle = Math.toDegrees(atan2(yOffset, xOffset));
        //adjust that angle to a global movement based off gyro data
        double adjustedAngle = angle - getWorldRotation();

        //set deltaX to the distance moved times cosine of the found angle
        double deltaX = hypot*cos(Math.toRadians(adjustedAngle));
        //set deltaY to the distance moved times sin of the found angle
        double deltaY = hypot*sin(Math.toRadians(adjustedAngle));

        //add the x and y offsets to the global x and y positions
        addDeviation(new FunctionLibrary.Point(deltaX, deltaY));
    }

    //overide the movement class
    @Override
    public void move(double x, double y, double rotation, double power) {
        //invert the x input as it's flipped
        x = -x;
        //define initial kinimatics based off of mecanum drive
        double pFrontLeft= x + y;
        double pFrontRight = x - y;
        double pBackLeft= x - y;
        double pBackRight = x + y;

        //+--+

        //find the motor with the highest power level
        double max = max(max(abs(pFrontRight),abs(pFrontLeft)),max(abs(pBackRight), abs(pBackRight)));

        //if that maximum power level is higher than the power given,
        //find the scaler value that will bring it down to that
        //and scale all of the motors using it
        if (max > power) {
            double scaler = power/max;
            pFrontLeft = pFrontLeft * scaler;
            pFrontRight = pFrontRight * scaler;
            pBackLeft = pBackLeft * scaler;
            pBackRight = pBackRight * scaler;
        }

        //add the rotation powers to the motors
        pFrontLeft = pFrontLeft + rotation;
        pFrontRight = pFrontRight - rotation;
        pBackLeft = pBackLeft + rotation;
        pBackRight = pBackRight - rotation;

        //find the motor with maximum power again
        max = max(max(abs(pFrontRight),abs(pFrontLeft)),max(abs(pBackRight), abs(pBackRight)));

        //if that maximum power level is higher than the power given,
        //find the scaler value that will bring it down to that
        //and scale all of the motors using it
        if (max > power) {
            double scaler = power/max;
            pFrontLeft = pFrontLeft * scaler;
            pFrontRight = pFrontRight * scaler;
            pBackLeft = pBackLeft * scaler;
            pBackRight = pBackRight * scaler;
        }

        //set all of the motors to those powers
        dcFrontLeft.setPower(pFrontLeft);
        dcFrontRight.setPower(pFrontRight);
        dcBackLeft.setPower(pBackLeft);
        dcBackRight.setPower(pBackRight);

        //check if the motors are set to run with encoders
        //if so, use kinimatics to find the encoder ticks they need to move
        //in order to reach the given position
        if (dcFrontLeft.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER) {

            dcFrontLeft.setTargetPosition((int)(x+y));
            dcFrontRight.setTargetPosition((int)(x-y));
            dcBackLeft.setTargetPosition((int)(x-y));
            dcBackRight.setTargetPosition((int)(x+y));
        }
    }
    //override the getDriveMotors function in order to provide
    //the drivemotors to the autoFunctions class
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
