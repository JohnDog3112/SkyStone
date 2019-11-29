package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware_Maps.RotatedHolonomicHardware;

@TeleOp(group = "A")
public class Camera extends LinearOpMode {
    private static final String VUFORIA_KEY = "AUJrAPb/////AAAAGV6Dp0zFW0tbif2eZk4u4LsrIQNxlQdiTbA2UJgYbEh7rb+s+Gg9soHReFwRRQz9xAiUcZi6d4jtD9+keLWR9xwcT+zJFSfdajjl89kWcf99HIxpWIMuNfAKhW83arD48Jnz/MTYxuBajilzcUxcPYQx24G/MeA6ZlyBhEauLXCKVrsdddL9kaEatPQx1MblEiH5wbdsMsXHz7w0B9CyEhQyZRLXb0zSbijn+JhHaHblBEk40x7gxkQYM1F+f+GfTrx5xR7ibvldNjRJ0obz1NJfuZugfW4R4vpV3C8Qebk7Jmy4YdL62Kb8W2Xk/S55jDhsdNW8rCPvVGJqjM5useObvRhomu0UT5EDH6hwOYxU";
    private VuforiaLocalizer vuforia;
    RotatedHolonomicHardware robot = new RotatedHolonomicHardware(this);
    @Override
    public void runOpMode() throws InterruptedException {

        robot.move(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x,1);


    }
}
