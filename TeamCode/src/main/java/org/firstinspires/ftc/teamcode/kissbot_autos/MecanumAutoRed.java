package org.firstinspires.ftc.teamcode.kissbot_autos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

@Autonomous
public class MecanumAutoRed extends LinearOpMode {
    double mmPerInch = 25.4;
    double waitTimeBright = 0.75;
    double waitTimeDark = 5;
    double waitTime = waitTimeBright;
    @Override
    public void runOpMode() throws InterruptedException {
        KissHardware robot = new KissHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        robot.initVuforia(hardwareMap);
        waitForStart();
        robot.SkystoneTrackables.activate();
        robot.setPosition(-17,-58.7);
        robot.setRotation(180);
        int nSwitch = 0;
        FunctionLibrary.Point destination = new FunctionLibrary.Point(0,0);
        FunctionLibrary.Point destination2 = new FunctionLibrary.Point(0,0);
        int nAlpha = robot.colorSensor.alpha();
        while (opModeIsActive()) {
            nAlpha = robot.colorSensor.alpha();
            int result = 0;
            switch(nSwitch) {
                case 0:
                    destination = new FunctionLibrary.Point(-18,-41);
                    result = auto.gotoPosition(destination,1,1,-180);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    break;
                case 1:
                    robot.updateVuforia();
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        Log.d("Found Stone","Time: " + getRuntime());
                        nSwitch=8;
                    }
                    if (getRuntime() > waitTime) nSwitch++;
                    break;
                case 2:
                    destination = new FunctionLibrary.Point(-26,-42);
                    result = auto.gotoPosition(destination,1,1,180);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    break;
                case 3:
                    robot.updateVuforia();
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        Log.d("Found Stone","Time: " + getRuntime());
                        nSwitch=8;
                    }
                    if (getRuntime() > waitTime) nSwitch++;
                    break;
                case 4:
                    destination = new FunctionLibrary.Point(-34.7,-42);
                    result = auto.gotoPosition(destination,1,1,180);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    break;
                case 5:
                    robot.updateVuforia();
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        Log.d("Found Stone","Time: " + getRuntime());
                        nSwitch=8;
                    }
                    if (getRuntime() > waitTime) nSwitch++;
                    break;
                case 8:
                    VectorF offset = robot.VuMarkPositions.get("Stone Target");
                    destination = new FunctionLibrary.Point(offset.get(1)/mmPerInch + robot.getX(), robot.getY());
                    destination2 = new FunctionLibrary.Point(offset.get(1)/mmPerInch + robot.getX(),-offset.get(0)/mmPerInch + robot.getY());
                    Log.d("Stone Target Pos", destination2.x + " " + destination2.y);
                    Log.d("Current Pos", robot.getX() + " " + robot.getY());
                    nSwitch++;
                case 9:
                    result = auto.gotoPosition(destination,1,1, 180);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    telemetry.addData("x", destination.x);
                    telemetry.addData("y", destination.y);
                    telemetry.update();
                    break;
                case 10:
                    if (getRuntime() > 0.5) nSwitch++;
                    break;
                case 11:
                    result = auto.gotoPosition(destination2, 1, 1, 180);
                    if (result < 0) nSwitch++;
                    break;
                case 12:
                    result = auto.gotoPosition(new FunctionLibrary.Point(-17,-58.7),0.5,0.5,180);
                    if (result < 0) nSwitch++;
                default:
                    break;


            }
        }
    }
}
