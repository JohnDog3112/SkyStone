package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Hardware_Maps.RotatedHolonomicHardware;

import static org.firstinspires.ftc.teamcode.Functions.FunctionLibrary.GetYaw;

@Autonomous
public class RotHolAutoRotation extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        RotatedHolonomicHardware robot = new RotatedHolonomicHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);

        double angle = 0;
        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.a) {
                angle = Math.toDegrees(Math.atan2(gamepad1.left_stick_y,gamepad1.left_stick_x)) + 90;
            }
            telemetry.addData("angle", angle);
            telemetry.update();
        }
        while (opModeIsActive()) {
            int response = auto.rotPID(angle,1, 5,30);
            double rotation = robot.getWorldRotation();
            telemetry.addData("Roation", rotation);
            telemetry.addData("angle",angle);
            telemetry.addData("rotationpower", ((rotation-angle)/180)/2);
            telemetry.addData("offset",rotation-angle);
            telemetry.update();
            if (response < 0) break;
        }
    }
}
