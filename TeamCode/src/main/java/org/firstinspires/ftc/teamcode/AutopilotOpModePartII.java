package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * By Narayan the GOAT
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
    private DcMotor Intake;
    private DcMotor Spinner;
    double drive;
    double turn;
    double strafe;
    double force;
    double spin;
    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    double intakePower;
    double spinnerPower;
    boolean protectionMode;
    double multiplier;
    int intakeSetting;
    int spinnerSetting;
    double intakeFactor;
    int i;
    boolean trackingMode;
    double spinFactor;
    boolean checker;
    boolean rotation;
    //private DcMotor Intake;
    //private DcMotor IntakeRight;
    //private DcMotor Treadmill;
    //private Servo FoundationServo;
    public double startTime = runtime.milliseconds();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = 0.0;
        turn = 0.0;
        strafe = 0.0;
        force = 0.0;
        spin = 0.0;
        frontLeftPower = 0.0;
        frontRightPower = 0.0;
        backLeftPower = 0.0;
        backRightPower = 0.0;
        intakePower = 0.0;
        spinnerPower = 0.0;
        protectionMode = true;
        multiplier = 0.25;
        intakeSetting = 1;
        spinnerSetting = 1;
        intakeFactor = 1.0;
        trackingMode = false;
        spinFactor = 0.0;
        checker = false;
        rotation = false;

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Spinner = hardwareMap.get(DcMotor.class, "Spinner");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);
        Spinner.setDirection(DcMotor.Direction.FORWARD);


        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Spinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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

    public boolean checking(boolean key) {
        if (key) {
            checker = true;
        }
        if (checker) {
            if (!key) {
                checker = false;
                return true;
            }
        }
        return false;
    }
    public void loop() {
        if (gamepad1.b) {
            if (gamepad1.right_trigger > 0.5) {
                /*(double y_coordinate = -gamepad1.left_stick_x;
                double x_coordinate = -gamepad1.left_stick_y;
                telemetry.addLine("tracking mode on");
                double distance = Math.sqrt(Math.pow(x_coordinate, 2) + Math.pow(y_coordinate, 2));
                telemetry.addLine(String.valueOf(distance));
                telemetry.addLine(String.valueOf(y_coordinate));
                telemetry.addLine(String.valueOf(x_coordinate));
                */
                telemetry.addData("encoder-front-left", FrontLeft.getCurrentPosition());
                telemetry.addData("encoder-back-left", BackLeft.getCurrentPosition());
                telemetry.addData("encoder-front-right", FrontRight.getCurrentPosition());
                telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());
                telemetry.update();
                idle();
                if (distance > 0.0) {
                    double angle = 50 * Math.asin(y_coordinate / distance);
                    telemetry.addLine(String.valueOf(angle));
                    int p = 0;
                    while (p < (300 * Math.abs(angle * y_coordinate))) {
                        if (x_coordinate < 0) {
                            frontLeftPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                        }
                        if (x_coordinate >= 0) {
                            frontLeftPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                        }
                        p++;
                    }
                    int j = 0;
                    while (j < (20000 * distance)) {
                        if (x_coordinate < 0) {
                            frontLeftPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                            j++;
                        }
                        if (x_coordinate >= 0) {
                            frontLeftPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            frontRightPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            backLeftPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            backRightPower = Range.clip(-1.0, -1.0, 1.0) * 0.8;
                            FrontLeft.setPower(0.5 * frontLeftPower);
                            FrontRight.setPower(0.5 * frontRightPower);
                            BackLeft.setPower(0.5 * backLeftPower);
                            BackRight.setPower(0.5 * backRightPower);
                            j++;
                        }
                    }
                    FrontLeft.setPower(0);
                    FrontRight.setPower(0);
                    BackLeft.setPower(0);
                    BackRight.setPower(0);
                    int k = 0;
                    while (k < 200000) {
                        intakePower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                        Intake.setPower(-intakePower);
                        k++;
                    }
                    Intake.setPower(0);
                }
            }
        } else {
            if (gamepad1.a) {
                protectionMode = false;
                multiplier = 1.0;
            }
            if (gamepad1.a && gamepad1.y) {
                protectionMode = true;
                multiplier = 0.25;
            }
            if (checking(gamepad1.dpad_right)) {

                intakeSetting = intakeSetting + 1;
                if (intakeSetting > 2) {
                    intakeSetting = 1;
                }
            }
            if (checking(gamepad1.dpad_left)) {
                spinnerSetting = spinnerSetting + 1;
                if (spinnerSetting > 2) {
                    spinnerSetting = 1;
                }
            }
            if (intakeSetting == 1) {
                intakeFactor = 1.0;
            }
            if (intakeSetting == 2) {
                intakeFactor = -1.0;
            }
            if (spinnerSetting == 1) {
                spinFactor = 1.0;
            }
            if (spinnerSetting == 2) {
                spinFactor = -1.0;
            }

            //Forward/backward and strafing with the left stick, turning with the right
            if (gamepad1.left_trigger < 0.5) {
                rotation = true;
            }
            if (rotation = true) {
                if (gamepad1.left_trigger < 0.5) {
                    rotation = false;
                }
            }
            if (rotation) {
                spin = 1.0;
            }
            if (!rotation) {
                spin = 0.0;
            }
            force = gamepad1.right_trigger;
            drive = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            turn = -gamepad1.right_stick_x;
            spinnerPower = Range.clip(spin, -1.0, 1.0) * 0.8;
            intakePower = Range.clip(force, -1.0, 1.0) * 0.8;
            frontLeftPower = Range.clip(drive + turn + strafe, -1.0, 1.0) * 0.8;
            frontRightPower = Range.clip(drive - turn - strafe, -1.0, 1.0) * 0.8;
            backLeftPower = Range.clip(drive + turn - strafe, -1.0, 1.0) * 0.8;
            backRightPower = Range.clip(drive - turn + strafe, -1.0, 1.0) * 0.8;

            //make sure left and right power are outside threshold


            // public double clip(double number, double min, double max)

            telemetry.addData("drive", drive);
            telemetry.addData("turn", turn);
            telemetry.addData("strafe", strafe);
            telemetry.addData("force", force);
            telemetry.addData("spin", spin);
            telemetry.addData("intakeSetting", intakeSetting);
            telemetry.addData("spinnerSetting", spinnerSetting);
            telemetry.update();
            FrontLeft.setPower(multiplier * frontLeftPower);
            FrontRight.setPower(multiplier * frontRightPower);
            BackLeft.setPower(multiplier * backLeftPower);
            BackRight.setPower(multiplier * backRightPower);
            Intake.setPower(intakeFactor * intakePower);
            Spinner.setPower(spinFactor * spinnerPower);
        }
        telemetry.update();
    }
}
