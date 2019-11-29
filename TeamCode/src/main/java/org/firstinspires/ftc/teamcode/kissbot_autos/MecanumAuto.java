package org.firstinspires.ftc.teamcode.kissbot_autos;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Function;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

@Autonomous
public class MecanumAuto extends LinearOpMode {
    double mmPerInch = 25.4;
    @Override
    public void runOpMode() throws InterruptedException {
        KissHardware robot = new KissHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        robot.initVuforia(hardwareMap);
        waitForStart();
        robot.SkystoneTrackables.activate();
        robot.setPosition(-17,58.7);
        robot.setRotation(0);
        int nSwitch = 0;
        FunctionLibrary.Point destination = new FunctionLibrary.Point(0,0);
        FunctionLibrary.Point destination2 = new FunctionLibrary.Point(0,0);
        while (opModeIsActive()) {
            int result = 0;
            switch(nSwitch) {
                case 0:
                    destination = new FunctionLibrary.Point(-18,40);
                    result = auto.gotoPosition(destination,0.5,0.5,0);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    break;
                case 1:
                    robot.updateVuforia();
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        Log.d("Found Stone","Time: " + getRuntime());
                        nSwitch=8;
                    }
                    if (getRuntime() > 0.75) nSwitch++;
                    break;
                case 2:
                    destination = new FunctionLibrary.Point(-26,40);
                    result = auto.gotoPosition(destination,1,0.5,0);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    break;
                case 3:
                    robot.updateVuforia();
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        Log.d("Found Stone","Time: " + getRuntime());
                        nSwitch=8;
                    }
                    if (getRuntime() > 0.75) nSwitch++;
                    break;
                case 4:
                    destination = new FunctionLibrary.Point(-34.7,40);
                    result = auto.gotoPosition(destination,1,0.5,0);
                    resetStartTime();
                    if (result < 0) nSwitch++;
                    break;
                case 5:
                    robot.updateVuforia();
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        Log.d("Found Stone","Time: " + getRuntime());
                        nSwitch=8;
                    }
                    if (getRuntime() > 0.75) nSwitch++;
                    break;
                case 8:
                    VectorF offset = robot.VuMarkPositions.get("Stone Target");
                    destination = new FunctionLibrary.Point(-offset.get(1)/mmPerInch + robot.getX(), robot.getY());
                    destination2 = new FunctionLibrary.Point(-offset.get(1)/mmPerInch + robot.getX(),offset.get(0)/mmPerInch + robot.getY());
                    nSwitch++;
                case 9:
                    result = auto.gotoPosition(destination,1,0.5, 0);
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
                    result = auto.gotoPosition(destination2, 1, 0.5, 0);
                    if (result < 0) nSwitch++;
                default:
                    break;


            }
        }
    }
}
