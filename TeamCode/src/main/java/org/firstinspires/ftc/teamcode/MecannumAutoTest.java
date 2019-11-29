package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

@Autonomous
public class MecannumAutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        KissHardware robot = new KissHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        robot.initVuforia(hardwareMap);
        waitForStart();
        robot.SkystoneTrackables.activate();
        int nSwitch = 0;
        int result = 10;
        FunctionLibrary.Point destinationPos = new FunctionLibrary.Point(0,0);
        FunctionLibrary.Point currentPos = new FunctionLibrary.Point(0,0);
        double distanceTicks = 0;
        double iterations = 0;
        VectorF SkystonePos = null;
        while(opModeIsActive()) {
            switch(nSwitch) {
                case 0:
                    currentPos = robot.getPosition();
                    distanceTicks = (19/robot.getWheelCircumfrance())*robot.dcFrontRight.getMotorType().getTicksPerRev();
                    destinationPos = new FunctionLibrary.Point(currentPos.x,currentPos.y+distanceTicks);
                    nSwitch++;
                    break;
                case 1:
                    currentPos = robot.getPosition();
                    telemetry.addData("y", destinationPos.y);
                    double gyroAngle = robot.getWorldRotation();
                    result = auto.gotoPosition(destinationPos,0.5, 86);
                    if (result < 0) nSwitch++;
                    telemetry.addData("gyro",gyroAngle);
                    telemetry.addData("result", result);
                    break;
                case 2:
                    if (robot.VuMarkPositions.containsKey("Stone Target")) {
                        SkystonePos = robot.VuMarkPositions.get("Stone Target");
                        robot.move(0,0,0,0);
                        nSwitch++;
                    } else if (getRuntime() > .5) {
                        nSwitch++;
                        iterations++;
                    }
                    if (iterations == 4) nSwitch = -1;
                    break;
                case 3:
                    currentPos = robot.getPosition();
                    distanceTicks = (7/robot.getWheelCircumfrance())*robot.dcFrontRight.getMotorType().getTicksPerRev();
                    destinationPos = new FunctionLibrary.Point(currentPos.x+distanceTicks,currentPos.y);
                    if (getRuntime() > 0.5) nSwitch++;
                    break;
                case 4:
                    currentPos = robot.getPosition();
                    result = auto.gotoPosition(destinationPos,0.5,86);
                    robot.updateVuforia();
                    if (result < 0) nSwitch=2;
                    break;
                default:
                    break;
            }
            telemetry.addData("state", nSwitch);
            telemetry.update();
        }

    }
}
