package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Functions.RobotConstructor;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.firstinspires.ftc.teamcode.Functions.FunctionLibrary.GetYaw;

public class RotatedHolonomicHardware extends RobotConstructor {

    private static final String VuforiaKey = "AUJrAPb/////AAAAGV6Dp0zFW0tbif2eZk4u4LsrIQNxlQdiTbA2UJgYbEh7rb+s+Gg9soHReFwRRQz9xAiUcZi6d4jtD9+keLWR9xwcT+zJFSfdajjl89kWcf99HIxpWIMuNfAKhW83arD48Jnz/MTYxuBajilzcUxcPYQx24G/MeA6ZlyBhEauLXCKVrsdddL9kaEatPQx1MblEiH5wbdsMsXHz7w0B9CyEhQyZRLXb0zSbijn+JhHaHblBEk40x7gxkQYM1F+f+GfTrx5xR7ibvldNjRJ0obz1NJfuZugfW4R4vpV3C8Qebk7Jmy4YdL62Kb8W2Xk/S55jDhsdNW8rCPvVGJqjM5useObvRhomu0UT5EDH6hwOYxU";
    //define the motors
    public final DcMotor mFront;
    public final DcMotor mBack;
    public final DcMotor mRight;
    public final DcMotor mLeft;

    private static double wheelDiameter = 4;
    private static double dKp = 0.001;
    private static double dMinMoveSpeed = 0.1;
    private static float CameraForwardDisplacement = (float)6;
    private static float CameraLeftDisplacement = (float)-2.5;
    private static float CameraVerticalDisplacement = (float)6.375;
    private static double rampingDistance = 12;
    private static double minMoveSpeed = 0.1;
    private static String webcameName = "Webcam 1";

    //this will take the inputs on initialization of the hMap.
    public RotatedHolonomicHardware(LinearOpMode opMode) {
        super(opMode, wheelDiameter, dKp, minMoveSpeed, rampingDistance, CameraForwardDisplacement, CameraLeftDisplacement, CameraVerticalDisplacement, webcameName, VuforiaKey);
        this.hMap = opMode.hardwareMap;
        mFront = hMap.dcMotor.get("mFront");
        mBack = hMap.dcMotor.get("mBack");
        mRight = hMap.dcMotor.get("mRight");
        mLeft = hMap.dcMotor.get("mLeft");

        mFront.setDirection(DcMotor.Direction.FORWARD);
        mBack.setDirection(DcMotor.Direction.REVERSE);
        mLeft.setDirection(DcMotor.Direction.FORWARD);
        mRight.setDirection(DcMotor.Direction.REVERSE);
        mFront.setPower(0);
        mBack.setPower(0);
        mRight.setPower(0);
        mLeft.setPower(0);

    }

    @Override
    public void move(double x, double y, double rotation, double power) {
        double pFront = -x;
        double pBack = -x;
        double pLeft = y;
        double pRight = y;

        double max = max(max(abs(pFront), abs(pBack)), max(abs(pLeft), abs(pRight)));
        if (max > power) {
            double scaler = power / max;
            pFront *= scaler;
            pBack *= scaler;
            pLeft *= scaler;
            pRight *= scaler;
        }
        pFront += rotation;
        pBack -= rotation;
        pLeft += rotation;
        pRight -= rotation;

        max = max(max(abs(pFront), abs(pBack)), max(abs(pLeft), abs(pRight)));
        if (max > 1) {
            double scaler = 1 / max;
            pFront *= scaler;
            pBack *= scaler;
            pLeft *= scaler;
            pRight *= scaler;
        }

        if (mFront.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER)
            mFront.setTargetPosition((int) x);
        if (mBack.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER)
            mBack.setTargetPosition((int) x);
        if (mLeft.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER)
            mLeft.setTargetPosition((int) y);
        if (mRight.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER)
            mRight.setTargetPosition((int) y);


        mFront.setPower(pFront);
        mBack.setPower(pBack);
        mLeft.setPower(pLeft);
        mRight.setPower(pRight);
    }
    double lastLeftPos = 0;
    double lastRightPos = 0;
    double lastFrontPos = 0;
    double lastBackPos = 0;
    @Override
    public void updateOdometry() {
        super.updateOdometry();
        double leftOffset = mLeft.getCurrentPosition()-lastLeftPos;
        double rightOffset = mRight.getCurrentPosition()-lastRightPos;
        double frontOffset = mFront.getCurrentPosition()-lastFrontPos;
        double backOffset = mBack.getCurrentPosition()-lastBackPos;

        lastLeftPos = mLeft.getCurrentPosition();
        lastRightPos = mRight.getCurrentPosition();
        lastFrontPos = mFront.getCurrentPosition();
        lastBackPos = mBack.getCurrentPosition();

        double yOffset = -(leftOffset+rightOffset)/2;
        double xOffset = -(frontOffset+backOffset)/2;

        double hypot = sqrt(pow(xOffset,2) + pow(yOffset,2));

        double angle = Math.toDegrees(atan2(yOffset, xOffset));
        double adjustedAngle = angle - getWorldRotation();

        double deltaX = hypot*cos(Math.toRadians(adjustedAngle));
        double deltaY = hypot*sin(Math.toRadians(adjustedAngle));

        addDeviation(new FunctionLibrary.Point(deltaX, deltaY));
    }
    @Override
    public DcMotor[] getDriveMotors() {
        DcMotor[] motors = new DcMotor[4];
        motors[0] = mFront;
        motors[1] = mBack;
        motors[2] = mLeft;
        motors[3] = mRight;
        return motors;
    }
}
