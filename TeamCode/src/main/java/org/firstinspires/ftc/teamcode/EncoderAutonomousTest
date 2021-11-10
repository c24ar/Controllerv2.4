package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Drive Encoder", group="Exercises")
private DcMotor FrontLeft; //Declared  but not initialized
private DcMotor FrontRight;
private DcMotor BackLeft;
private DcMotor BackRight;

public class DriveWithEncoder extends LinearOpMode
{
    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {

        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);

        // reset encoder counts kept by motors.
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set motors to run forward for 5000 encoder counts.
        FrontLeft.setTargetPosition(5000);
        BackLeft.setTargetPosition(5000);
        FrontRight.setTargetPosition(5000);
        BackRight.setTargetPosition(5000);

        // set motors to run to target encoder position and stop with brakes on.
        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Mode", "running");
        telemetry.update();

        // set both motors to 25% power. Movement will start. Sign of power is
        // ignored as sign of target encoder position controls direction when
        // running to position.

        FrontLeft.setPower(0.25);
        BackLeft.setPower(0.25);
        FrontRight.setPower(0.25);
        BackRight.setPower(0.25);

        // wait while opmode is active and left motor is busy running to position.

        while (opModeIsActive() && FrontLeft.isBusy())
        {
            telemetry.addData("encoder-front-left", FrontLeft.getCurrentPosition() + "  busy=" + FrontLeft.isBusy());
            telemetry.addData("encoder-back-left", BackLeft.getCurrentPosition() + "  busy=" + BackLeft.isBusy());
            telemetry.addData("encoder-front-right", FrontRight.getCurrentPosition() + "  busy=" + FrontRight.isBusy());
            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition() + "  busy=" + BackRight.isBusy());
            telemetry.update();
            idle();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        FrontLeft.setPower(0.0);
        BackLeft.setPower(0.0);
        FrontRight.setPower(0.0);
        BackRight.setPower(0.0);

        // wait 5 sec to you can observe the final encoder position.

        resetStartTime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-front-left-end", FrontLeft.getCurrentPosition());
            telemetry.addData("encoder-back-left-end", BackLeft.getCurrentPosition());
            telemetry.addData("encoder-front-right-end", FrontRight.getCurrentPosition());
            telemetry.addData("encoder-back-right-end", BackRight.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // Back to starting position without encoders(to measure precision)


        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FrontLeft.setTargetPosition(0);
        BackLeft.setTargetPosition(0);
        FrontRight.setTargetPosition(0);
        BackRight.setTargetPosition(0);
        
        
        FrontLeft.setPower(-0.25);
        BackLeft.setPower(-0.25);
        FrontRight.setPower(-0.25);
        BackRight.setPower(-0.25);

        while (opModeIsActive() && FrontLeft.getCurrentPosition() > frontLeft.getTargetPosition())
        {
            telemetry.addData("encoder-front-left", FrontLeft.getCurrentPosition());
            telemetry.addData("encoder-back-left", BackLeft.getCurrentPosition());
            telemetry.addData("encoder-front-right", FrontRight.getCurrentPosition());
            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // set motor power to zero to stop motors.

        FrontLeft.setPower(0.0);
        BackLeft.setPower(0.0);
        FrontRight.setPower(0.0);
        BackRight.setPower(0.0);

        resetStartTime();

        while (opModeIsActive() && getRuntime() < 5)
        {
            telemetry.addData("encoder-front-left-end", FrontLeft.getCurrentPosition());
            telemetry.addData("encoder-back-left-end", BackLeft.getCurrentPosition());
            telemetry.addData("encoder-front-right-end", FrontRight.getCurrentPosition());
            telemetry.addData("encoder-back-right-end", BackRight.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }
}
