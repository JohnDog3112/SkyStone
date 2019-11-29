package org.firstinspires.ftc.teamcode.Calibrations;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@TeleOp
public class CheckAllEncoders extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        for (Map.Entry<String, Servo> entry : hardwareMap.servo.entrySet()) {
            entry.getValue().setPosition(entry.getValue().getPosition());
        }
        for (Map.Entry<String,DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
            entry.getValue().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }
        int selectedMotor1 = 0;
        int selectedMotor2 = 0;

        int selectedServo = 0;
        int selectedServo2 = 0;
        while (!isStopRequested()) {
            ArrayList<Map.Entry<String,DcMotor>> motors = new ArrayList<>();
            ArrayList<Map.Entry<String,CRServo>> crServos = new ArrayList<>();
            ArrayList<Map.Entry<String,Servo>> servos = new ArrayList<>();
            boolean run = true;

            for (Map.Entry<String,DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
                telemetry.addData(entry.getKey(),entry.getValue().getCurrentPosition());
                motors.add(entry);
            }
            for (Map.Entry<String, CRServo> entry: hardwareMap.crservo.entrySet()) {
                telemetry.addData(entry.getKey(),entry.getValue().getPower() + ", " + entry.getValue().getController().getServoPosition(entry.getValue().getPortNumber()));
                crServos.add(entry);
            }
            for (Map.Entry<String, Servo> entry : hardwareMap.servo.entrySet()) {
                telemetry.addData(entry.getKey(),entry.getValue().getPosition());
                servos.add(entry);
            }
            for (Map.Entry<String, ColorSensor> entry : hardwareMap.colorSensor.entrySet()) {
                telemetry.addData(entry.getKey(),entry.getValue().alpha());
                entry.getValue().enableLed(true);

            }
            for (Map.Entry<String, TouchSensor> entry : hardwareMap.touchSensor.entrySet()) {
                telemetry.addData(entry.getKey(), entry.getValue().isPressed());
                if (entry.getValue().isPressed()) run = false;
            }
            if (getRuntime() > 0.5) {
                if (gamepad1.dpad_up) {
                    selectedMotor1 += 1;
                    selectedMotor1 %= motors.size()-1;
                    resetStartTime();
                } else if (gamepad1.dpad_down) {
                    if (selectedMotor1 == 0) {
                        selectedMotor1 = motors.size()-1;
                    } else {
                        selectedMotor1 -= 1;
                    }
                    resetStartTime();
                }
                if (gamepad1.y) {
                    selectedMotor2 += 1;
                    selectedMotor2 %= motors.size()-1;
                    resetStartTime();
                } else if (gamepad1.a) {
                    if (selectedMotor2 == 0) {
                        selectedMotor2 = motors.size()-1;
                    } else {
                        selectedMotor2 -= 1;
                    }
                    resetStartTime();
                }
                if (gamepad2.dpad_up) {
                    selectedServo += 1;
                    selectedServo %= servos.size()+crServos.size()-1;
                    resetStartTime();
                } else if (gamepad2.dpad_down) {
                    if (selectedServo == 0) {
                        selectedServo = servos.size()+crServos.size()-1;
                    } else {
                        selectedServo -= 1;
                    }
                    resetStartTime();
                }
                if (gamepad2.y) {
                    selectedServo += 1;
                    selectedServo %= servos.size()+crServos.size()-1;
                    resetStartTime();
                } else if (gamepad2.a) {
                    if (selectedServo2 == 0) {
                        selectedServo2 = servos.size()+crServos.size()-1;
                    } else {
                        selectedServo2 -= 1;
                    }
                    resetStartTime();
                }


            }
            telemetry.addData("Motor 1", motors.get(selectedMotor1).getKey());
            telemetry.addData("Motor 2", motors.get(selectedMotor2).getKey());
            if (selectedServo < crServos.size()-1) telemetry.addData("Servo 1", crServos.get(selectedServo).getKey());
            else telemetry.addData("Servo 1", servos.get(selectedServo-crServos.size()));

            if (selectedServo2 < crServos.size()-1) telemetry.addData("Servo 2", crServos.get(selectedServo2).getKey());
            else telemetry.addData("Servo 2", servos.get(selectedServo2-crServos.size()));
            if (run) {
                motors.get(selectedMotor1).getValue().setPower(gamepad1.left_stick_y);
                motors.get(selectedMotor2).getValue().setPower(gamepad1.right_stick_y);
                if (gamepad1.left_bumper) motors.get(selectedMotor1).getValue().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                if (gamepad1.right_bumper) motors.get(selectedMotor2).getValue().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                if (selectedServo < crServos.size()-1) crServos.get(selectedServo).getValue().setPower(gamepad2.left_stick_y);
                else servos.get(selectedServo-crServos.size()).getValue().setPosition(servos.get(selectedServo-crServos.size()).getValue().getPosition()+gamepad2.left_stick_y);

                if (selectedServo2 < crServos.size()-1) crServos.get(selectedServo2).getValue().setPower(gamepad2.right_stick_y);
                else servos.get(selectedServo2-crServos.size()).getValue().setPosition(servos.get(selectedServo2-crServos.size()).getValue().getPosition()+gamepad2.right_stick_y);
            } else {
                for (Map.Entry<String,DcMotor> entry : motors) {
                    entry.getValue().setPower(0);
                }
                for (Map.Entry<String,CRServo> entry : crServos) {
                    entry.getValue().setPower(0);
                }
            }


            telemetry.update();
        }
    }
}
