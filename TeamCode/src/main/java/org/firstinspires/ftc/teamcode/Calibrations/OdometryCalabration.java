package org.firstinspires.ftc.teamcode.Calibrations;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Functions.AutoConfig;
import org.firstinspires.ftc.teamcode.Functions.RobotConstructor;
import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.firstinspires.ftc.teamcode.Functions.FunctionLibrary.GetYaw;

@Autonomous
public class OdometryCalabration extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        for (Map.Entry<String,DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
            entry.getValue().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        waitForStart();
        robot.setRotation(0);
        resetStartTime();
        while (!isStopRequested() && getRuntime() < 1) {
            telemetry.addData("runtime", getRuntime());
            telemetry.update();
        }
        if (opModeIsActive()) {
            robot.move(0,0,1,1);
            while (Math.abs(robot.getGyroRotation()) < 90 && opModeIsActive()) {
                telemetry.addData("runtime", getRuntime());
                telemetry.update();
            }
            robot.move(0,0,0,0);
            if (opModeIsActive()) {
                resetStartTime();
                while (opModeIsActive() && getRuntime() < 5);
                double rotation = robot.getGyroRotation();
                double horizontalTickOffsetPerDegree = robot.horizontalEncoder.getCurrentPosition()/rotation;
                double verticalTickOffsetPerDegree = robot.verticalEncoder.getCurrentPosition()/rotation;
                File horizontalPerTick = AppUtil.getInstance().getSettingsFile("horizontalTickOffsetPerDegree");
                if (!horizontalPerTick.exists()) {
                    try {
                        horizontalPerTick.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ReadWriteFile.writeFile(horizontalPerTick, horizontalTickOffsetPerDegree+ "");

                File verticalPerTick = AppUtil.getInstance().getSettingsFile("verticalTickOffsetPerDegree");
                if (!verticalPerTick.exists()) {
                    try {
                        verticalPerTick.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ReadWriteFile.writeFile(verticalPerTick, verticalTickOffsetPerDegree + "");
                telemetry.addData("horizontal: ", horizontalTickOffsetPerDegree);
                telemetry.addData("vertical: ", verticalTickOffsetPerDegree);
                telemetry.update();
            }
        }

        while (opModeIsActive());


    }
}
