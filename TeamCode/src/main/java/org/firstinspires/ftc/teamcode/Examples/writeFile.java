package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Functions.AutoConfig;
import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;

import java.io.File;
import java.io.IOException;

@TeleOp
public class writeFile extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("rotation:", robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
            telemetry.update();
        }
    }
}
