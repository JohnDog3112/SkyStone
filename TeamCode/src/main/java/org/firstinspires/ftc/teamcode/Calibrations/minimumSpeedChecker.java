package org.firstinspires.ftc.teamcode.Calibrations;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Map;

@Autonomous
public class minimumSpeedChecker extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double startSpeed = 0.1;
        waitForStart();
        resetStartTime();
        for (Map.Entry<String,DcMotor> motorEntry : hardwareMap.dcMotor.entrySet()) {
            double currentAdder = startSpeed;
            boolean notDone = true;
            DcMotor motor = motorEntry.getValue();
            while (notDone && !isStopRequested()) {
                resetStartTime();
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                motor.setPower(currentAdder);
                telemetry.addData("runTime", getRuntime());
                while (getRuntime() < 0.5);
                motor.setPower(0);
                if (motor.getCurrentPosition() > 10) {
                    currentAdder /= 10;
                } else {
                    notDone = false;
                }
            }
            notDone = true;
            double newAdder = currentAdder*2;
            while (notDone && !isStopRequested()) {
                resetStartTime();
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                motor.setPower(newAdder);
                telemetry.addData("runTime", getRuntime());
                while (getRuntime() < 0.5);
                motor.setPower(0);
                if (motor.getCurrentPosition() < 10) {
                    newAdder += currentAdder;
                } else {
                    notDone = false;
                }
            }
            telemetry.addData(motorEntry.getKey(), newAdder);
        }
        telemetry.update();
        while (opModeIsActive());
    }
}
