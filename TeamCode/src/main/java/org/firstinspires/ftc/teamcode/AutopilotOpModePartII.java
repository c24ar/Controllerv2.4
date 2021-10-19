package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Jordan Paglione on 10/16/20.
 * Edited by Narayan Topalli on 10/18/21
 */
//@Disabled
@TeleOp(name="Autopilot OpMode", group="Iterative Opmode")
public class AutopilotOpModePartII extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime(); //Declared AND Initialized
    private DcMotor FrontLeft; //Declared  but not initialized
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;
    double drive;
    double turn;
    double strafe;
    double realdrive = -gamepad1.left_stick_y;
    double realstrafe = gamepad1.left_stick_x;
    boolean autopilot = false;
    //private DcMotor Intake;
    //private DcMotor IntakeRight;
    //private DcMotor Treadmill;
    //private Servo FoundationServo;
    public double startTime = runtime.milliseconds();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);


        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }


    @Override
    public void init_loop() {
        //Servo1.setPosition(0.3);
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    public void isAutopilot() {
        if (Math.pow(realdrive, 2) + Math.pow(realstrafe, 2) > 0.9) {
            autopilot = true;
            if ((realdrive * drive < 0) || (realstrafe * strafe < 0)) {
                autopilot = false;
            }
        }
        else {
            autopilot = false;
        }
    }

    public void loop() {
        double threshold = 0.2;

        if (gamepad1.right_stick_x < -threshold ||  gamepad1.right_stick_x > threshold || gamepad1.left_stick_y < -threshold || gamepad1.left_stick_y > threshold || gamepad1.left_stick_x < -threshold || gamepad1.left_stick_x > threshold) {
            //Forward/backward and strafing with the left stick, turning with the right

            isAutopilot();

            if (autopilot = false) {
                drive = -gamepad1.left_stick_y;
                turn = gamepad1.right_stick_x;
                strafe = gamepad1.left_stick_x;
            }

            //make sure left and right power are outside threshold
            double frontLeftPower = Range.clip(drive + turn + strafe, -1.0, 1.0) * 0.8;
            double frontRightPower = Range.clip(drive - turn - strafe, -1.0, 1.0) * 0.8;
            double backLeftPower = Range.clip(drive + turn - strafe, -1.0, 1.0) * 0.8;
            double backRightPower = Range.clip(drive - turn + strafe, -1.0, 1.0) * 0.8;


            // public double clip(double number, double min, double max)

            if (Math.abs(frontLeftPower) > threshold || Math.abs(backLeftPower) > threshold || Math.abs(frontRightPower) > threshold || Math.abs(backRightPower) > threshold) {
                FrontLeft.setPower(frontLeftPower);
                BackLeft.setPower(backLeftPower);
                FrontRight.setPower(frontRightPower);
                BackRight.setPower(backRightPower);

            } else {
                FrontRight.setPower(0);
                FrontLeft.setPower(0);
                BackLeft.setPower(0);
                BackRight.setPower(0);
            }

        } else {
            FrontRight.setPower(0);
            FrontLeft.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }





        // Show the elapsed game time
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();
    }
}