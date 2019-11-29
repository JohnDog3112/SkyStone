package org.firstinspires.ftc.teamcode.Calibrations;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Functions.AutoConfig;
import org.firstinspires.ftc.teamcode.Functions.RobotConstructor;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.firstinspires.ftc.teamcode.Functions.FunctionLibrary.GetYaw;

public class OdometryCalabration extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        String frontLeftName = "frontLeft";
        String frontRightName = "frontRight";
        String backLeftName = "backLeft";
        String backRightName = "backRight";
        String verticalEncoderName= "frontLeft";
        String horizontalEncoderName = "frontRight";

        BNO055IMU imu;

        DcMotor dcFrontLeft = hardwareMap.dcMotor.get(frontLeftName);
        DcMotor dcFrontRight = hardwareMap.dcMotor.get(frontRightName);
        DcMotor dcBackLeft = hardwareMap.dcMotor.get(backLeftName);
        DcMotor dcBackRight = hardwareMap.dcMotor.get(backRightName);
        DcMotor dcVerticalEncoder = hardwareMap.dcMotor.get(verticalEncoderName);
        DcMotor dcHorizontalEncoder = hardwareMap.dcMotor.get(horizontalEncoderName);

        dcFrontRight.setDirection(DcMotor.Direction.REVERSE);
        dcBackRight.setDirection(DcMotor.Direction.REVERSE);

        dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcVerticalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcHorizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dcFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dcFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters BNparameters = new BNO055IMU.Parameters();
        BNparameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        BNparameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        BNparameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        BNparameters.loggingEnabled = true;
        BNparameters.loggingTag = "IMU";
        BNparameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //define the IMU from the hardwaremap
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        //initialize the IMU
        imu.initialize(BNparameters);
        waitForStart();
        if (opModeIsActive()) {
            dcFrontLeft.setPower(1);
            dcFrontRight.setPower(-1);
            dcBackLeft.setPower(1);
            dcBackRight.setPower(-1);
            while (GetYaw(0, imu) < 90 && opModeIsActive()) ;
            dcFrontLeft.setPower(0);
            dcFrontRight.setPower(0);
            dcBackLeft.setPower(0);
            dcBackRight.setPower(0);
            if (opModeIsActive()) {
                double rotation = GetYaw(0,imu);
                double horizontalTickOffsetPerDegree = dcHorizontalEncoder.getCurrentPosition()/rotation;
                double verticalTickOffsetPerDegree = dcVerticalEncoder.getCurrentPosition()/rotation;
                if (!AppUtil.getInstance().getSettingsFile("odometryCalabrations").exists()) {
                    try {
                        AutoConfig.createFile("odometryCalabrations",  new String[] {"HardwareMap", "horizontalTickOffsetPerDegree", "verticalTickOffsetPerDegree"});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ArrayList<HashMap<String,String>> data = new ArrayList<>();
                HashMap<String, String> odometryData = new HashMap<>();
                odometryData.put("HardwareMap", Integer.toString(hardwareMap.hashCode()));
                odometryData.put("horizontalTickOffsetPerDegree", Double.toString(horizontalTickOffsetPerDegree));
                odometryData.put("verticalTickOffsetPerDegree", Double.toString(verticalTickOffsetPerDegree));
                data.add(odometryData);
                AutoConfig.writeToDataFile("odometryCalabrations", data);
            }
        }



    }
}
