package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;

@Autonomous
public class testAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        waitForStart();
        robot.setRotation(90);
        while (opModeIsActive()) {
            telemetry.addData("rotation", robot.getWorldRotation());
            telemetry.update();
        }
    }
}
