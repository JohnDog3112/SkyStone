package org.firstinspires.ftc.teamcode.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

@Autonomous
public class KissBothAuto extends LinearOpMode {
    private final double dMTFOffset = 7.25;
    private int nSwitch = 0;
    private static final double mmPerInch = 25.4;
    FunctionLibrary.Point destination = new FunctionLibrary.Point(0,0);
    FunctionLibrary.Point SkystoneTarget = new FunctionLibrary.Point(0,0);
    private double result = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        robot.initVuforia(hardwareMap);
        waitForStart();
        robot.SkystoneTrackables.activate();
        FunctionLibrary.Point startingPosition = new FunctionLibrary.Point(-72+dMTFOffset,-9);
        double startingRotation = 90;
        robot.setPosition(startingPosition);
        robot.setRotation(startingRotation);
        while (opModeIsActive()) {
            Log.d("x", robot.getX() + "");
            Log.d("y", robot.getY() + "");
            Log.d("rotation", robot.getWorldRotation() + "");
            Log.d("state", nSwitch + "");
            switch(nSwitch) {
                case 0:
                    destination = new FunctionLibrary.Point(-36-robot.CameraForwardDisplacement,-24-8+robot.CameraLeftDisplacement);
                    result = auto.gotoPosition(destination, 1, 0.5,90);
                    if (result < 0) {
                        resetStartTime();
                        nSwitch++;
                    }
                    break;
                case 1:
                    robot.updateVuforia();
                    if(robot.VuMarkPositions.containsKey("Stone Target")) {
                        resetStartTime();
                        VectorF stonePos = robot.VuMarkPositions.get("Stone Target");
                        SkystoneTarget = new FunctionLibrary.Point((-stonePos.get(0)/mmPerInch)+robot.getX(),(stonePos.get(1)/mmPerInch)+robot.getY());
                        Log.d("BothAutoVuforia", "x: " + SkystoneTarget.x + "y: " + SkystoneTarget.y);
                        nSwitch++;
                    } else if (getRuntime() > 2) {
                        SkystoneTarget = new FunctionLibrary.Point(-24,-48+4);
                        resetStartTime();
                        nSwitch++;
                    }
                    break;
                case 2:
                    destination = new FunctionLibrary.Point(-36-robot.CameraForwardDisplacement,-24);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 3:
                    destination = new FunctionLibrary.Point(0,-18);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 4:
                    destination = new FunctionLibrary.Point(0,-24);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 5:
                    destination = new FunctionLibrary.Point(0,SkystoneTarget.y);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 6:
                    destination = new FunctionLibrary.Point(SkystoneTarget.x-12, SkystoneTarget.y);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 7:
                    destination = new FunctionLibrary.Point(-48, -12);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 8:
                    destination = new FunctionLibrary.Point(-48,24);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 9:
                    destination = new FunctionLibrary.Point(-48,18);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 10:
                    destination = new FunctionLibrary.Point(-24,24);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 11:
                    destination = new FunctionLibrary.Point(0,24);
                    result = auto.gotoPosition(destination,1,1,-45);
                    if (result < 0) nSwitch++;
                    break;
                case 12:
                    destination = new FunctionLibrary.Point(-24,48);
                    result = auto.gotoPosition(destination,1,1,-45);
                    if (result < 0) nSwitch++;
                    break;
                case 13:
                    destination = new FunctionLibrary.Point(-18,48);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 14:
                    destination = new FunctionLibrary.Point(-36,48);
                    result = auto.gotoPosition(destination,1,1,-90);
                    break;
                case 15:
                    destination = new FunctionLibrary.Point(-36,0);
                    result = auto.gotoPosition(destination,1,1,180);
                    if (result < 0) nSwitch++;
                    break;



            }
        }
    }
}
