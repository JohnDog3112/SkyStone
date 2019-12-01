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

import static org.firstinspires.ftc.teamcode.Functions.FunctionLibrary.GetYaw;

@Autonomous
public class OdometryCalabration extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        waitForStart();
        robot.setRotation(0);
        resetStartTime();
        while (!isStopRequested() && getRuntime() < 1);
        if (opModeIsActive()) {
            robot.move(0,0,1,1);
            while (Math.abs(robot.getGyroRotation()) < 90 && opModeIsActive()) ;
            robot.move(0,0,0,0);
            if (opModeIsActive()) {
                double rotation = robot.getGyroRotation();
                double horizontalTickOffsetPerDegree = robot.horizontalEncoder.getCurrentPosition()/rotation;
                double verticalTickOffsetPerDegree = robot.verticalEncoder.getCurrentPosition()/rotation;
                File horizontalPerTick = AppUtil.getInstance().getSettingsFile("horizontalTickOffsetPerDegree");
                ReadWriteFile.writeFile(horizontalPerTick, horizontalTickOffsetPerDegree+ "");

                File verticalPerTick = AppUtil.getInstance().getSettingsFile("verticalTickOffsetPerDegree");
                ReadWriteFile.writeFile(verticalPerTick, verticalTickOffsetPerDegree + "");
            }
        }
        while (opModeIsActive());


    }
}
