package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class Postnut extends LinearOpMode {
    public void runOpMode(){
        Drivetrain drivetrain = new Drivetrain(this);
        Mechanisms mechanisms = new Mechanisms(this);

        waitForStart();

        drivetrain.initDrivetrain(hardwareMap);
        mechanisms.initMechanisms(hardwareMap);

        double pivotPos = 0;

        double speedScale = 0.8;

        while (opModeIsActive()) {
            mechanisms.rightHanging.setPosition(0.13);

            //driving motion
            double drive = (-1 * (gamepad1.left_stick_y));
            double strafe = (gamepad1.left_stick_x);
            double rotate = (gamepad1.right_stick_x);
            double FL = speedScale * (drive + strafe + rotate);
            double FR = speedScale * (drive - strafe - rotate);
            double BL = speedScale * (drive - strafe + rotate);
            double BR = speedScale * (drive + strafe - rotate);

            drivetrain.frontLeft.setPower(FL);
            drivetrain.frontRight.setPower(FR);
            drivetrain.backLeft.setPower(BL);
            drivetrain.backRight.setPower(BR);

            //axle motion
            if (gamepad2.dpad_left) {
                mechanisms.rotateAxle("up");
            } else if (gamepad2.dpad_right) {
                mechanisms.rotateAxle("down");
            }

            // slide motion
            if (gamepad2.dpad_up) {
                mechanisms.extendSlide("up");
            } else if (gamepad2.dpad_down) {
                mechanisms.extendSlide("down");
            }

            // rotate pivot servo
            if (gamepad2.y) {
                mechanisms.setPivotScore();
                pivotPos = mechanisms.pivotScore;
            }
            if (gamepad2.a) {
                mechanisms.setPivotGrab();
                pivotPos = mechanisms.pivotGrab;
            }
            if (gamepad1.x) {
                pivotPos -= 0.01;
                mechanisms.pivot.setPosition(pivotPos);
            }
            if (gamepad1.b) {
                pivotPos += 0.01;
                mechanisms.pivot.setPosition(pivotPos);
            }

            //claw motion
            //open
            if (gamepad2.left_bumper) {
                mechanisms.openClaws();
            }
            //close
            if (gamepad2.right_bumper) {
                mechanisms.closeClaws();
            }


            //hanging motion
            if (gamepad2.right_stick_button) {
                mechanisms.releaseHooks();
            }
        }
    }
}
