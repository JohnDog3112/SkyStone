package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

@Autonomous
public class Both_Auto extends LinearOpMode {
    private KissHardware robot;
    private AutoFunctions auto;
    int nSwitch = 0;
    int runState = 0;
    double delay = 0;
    double dMTFOffset = 7.25;
    int blockPos = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new KissHardware(this);
        auto = new AutoFunctions(robot);
        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.a) {
                runState = 0;
            }
            if (gamepad1.b) {
                runState = 1;
            }
            if (gamepad1.right_bumper) blockPos = 0;
            if (gamepad1.left_bumper) blockPos = 1;
            if (gamepad1.back) blockPos = 2;
            if (getRuntime() > 0.1) {
                if (gamepad1.dpad_up) {
                    delay++;
                    telemetry.addData("dpad_up", true);
                    resetStartTime();
                } else if (gamepad1.dpad_down && delay > 0) {
                    delay--;
                    resetStartTime();
                }
            }
            telemetry.addData("runState:", runState);
            telemetry.addData("Delay", delay);
            telemetry.addData("currenttime", getRuntime());
            telemetry.addData("blockPos", blockPos);
            telemetry.update();
        }
        resetStartTime();
        while (getRuntime() < delay && opModeIsActive()){
            telemetry.addData("Seconds to start:", delay-getRuntime());
            telemetry.update();
        }
        FunctionLibrary.Point destination = new FunctionLibrary.Point(0,0);
        double result = 0;
        while (opModeIsActive()) {
            telemetry.addData("runState", runState);
            telemetry.addData("nSwitch", nSwitch);
            telemetry.addData("x", robot.getX());
            telemetry.addData("y",robot.getY());
            telemetry.addData("rotation", robot.getWorldRotation());
            telemetry.addData("startingX", -72+dMTFOffset);
            telemetry.addData("startingY", -12);
            switch(runState) {
                case 0:
                    switch(nSwitch) {
                        case 0:
                            robot.setPosition(-72+dMTFOffset,-12);
                            robot.setRotation(90);
                            nSwitch = blockPos+1;
                            break;
                        case 1:
                            destination = new FunctionLibrary.Point(-40-dMTFOffset,-24-0-4);
                            result = auto.gotoPosition(destination,1,1,90);
                            if (result < 0) nSwitch = -1;
                            break;
                        case 2:
                            destination = new FunctionLibrary.Point(-40-dMTFOffset,-24-8-4);
                            result = auto.gotoPosition(destination,1,1,90);
                            if (result <0) nSwitch = -1;
                            break;
                        case 3:
                            destination = new FunctionLibrary.Point(-40-dMTFOffset,-24-16-4);
                            result = auto.gotoPosition(destination,1,1,90);
                            if (result <0) nSwitch = -1;
                            break;
                    }
                break;
                case 1:
                    switch(nSwitch) {
                        case 0:
                            destination = new FunctionLibrary.Point(-12,-6);
                            result = auto.gotoPosition(destination,1,1,0);
                            if (result < 0) nSwitch++;
                            break;
                        case 1:
                            destination = new FunctionLibrary.Point(0,0);
                            result = auto.gotoPosition(destination,1,0.5,0);
                            if (result < 0) nSwitch++;
                            break;
                    }
                break;
            }
            telemetry.addData("destinationX", destination.x);
            telemetry.addData("destinationY", destination.y);
            telemetry.update();
        }
    }
}
