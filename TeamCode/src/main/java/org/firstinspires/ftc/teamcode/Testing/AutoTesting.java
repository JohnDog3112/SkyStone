package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Hardware_Maps.RotatedHolonomicHardware;
@Autonomous
public class AutoTesting extends LinearOpMode {
    int nSwitch = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        RotatedHolonomicHardware robot = new RotatedHolonomicHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        waitForStart();
        while (opModeIsActive()) {
            int result = 0;
            switch (nSwitch) {
                case 0:
                    result = auto.MoveRotHolPID(10, 0, 0.5, 5, 0);
                    if (result < 0) nSwitch++;
                    break;
                case 1:
                    result = auto.rotPID(180, 1, 5, 5);
                    if (result < 0) nSwitch++;
                    break;
                case 2:
                    result = auto.MoveRotHolPID(10, 0, 1, 5, 180);
                    if (result < 0) nSwitch++;
                    break;
                case 3:
                    result = auto.rotPID(0,1,5,5);
                    if (result < 0) nSwitch++;
                    break;
                default:
                    stop();
                    break;
            }
        }

    }
}
