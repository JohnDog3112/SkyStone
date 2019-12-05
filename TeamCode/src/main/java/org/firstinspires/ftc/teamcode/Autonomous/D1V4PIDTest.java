package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctionsD1V4;
import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;

@Autonomous
public class D1V4PIDTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        AutoFunctionsD1V4 auto = new AutoFunctionsD1V4(robot);
        waitForStart();
        double result = 0;
        while (opModeIsActive() && !(result < 0)) {
            result = auto.MovePID(12,0,1,30,0);
            telemetry.addData("result: ", result);
            telemetry.update();
        }
        while (opModeIsActive());
    }
}
