package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
    private DcMotor Intake2;
    private DcMotor Slide;
    private Servo Arm;
    private Servo Claw;
    double MIN_POSITION;
    double MAX_POSITION;
    double armPos;
    double clawPos;
    double drive;
    double turn;
    double strafe;
    double force;
    double spin;
    double slide;
    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    double intakePower;
    double spinnerPower;
    double slidePower;
    double multiplier;
    int intakeSetting;
    int spinnerSetting;
    double intakeFactor;
    int i;
    boolean trackingMode;
    double spinFactor;
    boolean checker;
    boolean rotation;
    public double startTime = runtime.milliseconds();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = 0.0;
        turn = 0.0;
        strafe = 0.0;
        force = 0.0;
        spin = 0.0;
        slide = 0.0;
        frontLeftPower = 0.0;
        frontRightPower = 0.0;
        backLeftPower = 0.0;
        backRightPower = 0.0;
        intakePower = 0.0;
        spinnerPower = 0.0;
        slidePower = 0.0;
        multiplier = 1.0;
        intakeSetting = 1;
        spinnerSetting = 1;
        intakeFactor = 1.0;
        trackingMode = false;
        spinFactor = 0.0;
        checker = false;
        rotation = false;
        MIN_POSITION = 0;
        MAX_POSITION = 1;
        armPos = 0.5;
        clawPos = 0.9;

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Spinner = hardwareMap.get(DcMotor.class, "Spinner");
        Intake2 = hardwareMap.get(DcMotor.class, "Intake2");
        Slide = hardwareMap.get(DcMotor.class, "Slide");
        Arm = hardwareMap.get(Servo.class, "Arm");
        Claw = hardwareMap.get(Servo.class, "Claw");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);
        Spinner.setDirection(DcMotor.Direction.FORWARD);
        Intake2.setDirection(DcMotor.Direction.FORWARD);
        Slide.setDirection(DcMotor.Direction.FORWARD);


        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Spinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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
    //key press function
    public boolean checking(boolean key) {
        if (key) {
            this.checker = true;
        }
        if (this.checker) {
            if (!key) {
                this.checker = false;
                return true;
            }
        }
        return false;
    }
    public void loop() {
        //tracking object mode: User input towards object and robot automatically connects
        if (gamepad1.b) {
            if (gamepad1.right_trigger > 0.5) {
                double y_coordinate = -gamepad1.left_stick_x;
                double x_coordinate = -gamepad1.left_stick_y;
                telemetry.addLine("tracking mode on");
                double distance = Math.sqrt(Math.pow(x_coordinate, 2) + Math.pow(y_coordinate, 2));
                telemetry.update();
                if (distance > 0.0) {
                    //distance input is determined by stick position magnitude
                    //angle input is determined by stick position angle
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
                    //lines above control how motors are activated to conform to angle and distance values
                    FrontLeft.setPower(0);
                    FrontRight.setPower(0);
                    BackLeft.setPower(0);
                    BackRight.setPower(0);
                    int k = 0;
                    while (k < 200000) {
                        intakePower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                        Intake.setPower(-intakePower);
                        Intake2.setPower(intakePower);
                        k++;
                    }
                    Intake.setPower(0);
                    Intake2.setPower(0);
                }
            }
        } else {
            //Claw + arm controls
            armPos = (Math.pow(gamepad2.right_trigger, 3))/2.5 + 0.6;
            if (armPos < 0.65) {
                clawPos = 0.3;
            }
            if (gamepad2.b) {
                clawPos = 0.3;
            }
            else {
                clawPos = 0.9;
            }
            //Slide controls
            if (gamepad2.y) {
                slide = -1.0;
            }
            if (gamepad2.a) {
                slide = 1.0;
            }
            if (!gamepad2.a && !gamepad2.y) {
                slide = 0.0;
            }
            //Intake + Spinner settings
            if (gamepad1.dpad_up) {
                intakeSetting = 1;
            }
            if (gamepad1.dpad_down) {
                intakeSetting = 2;
            }
            if (gamepad1.dpad_left) {
                spinnerSetting = 1;
            }
            if (gamepad1.dpad_right) {
                spinnerSetting = 2;
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

            //Using the spinner
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
            //Movement variables based on user inputs
            force = gamepad1.right_trigger;
            drive = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            turn = -gamepad1.right_stick_x;
/*            ArmPos = Range.clip(ArmPos, MIN_POSITION, MAX_POSITION);
            ClawPos = Range.clip(ClawPos, MIN_POSITION, MAX_POSITION);*/
            spinnerPower = Range.clip(spin, -1.0, 1.0) * 0.8;
            intakePower = Range.clip(force, -1.0, 1.0) * 0.8;
            slidePower = Range.clip(slide, -1.0, 1.0) * 0.4;
            frontLeftPower = Range.clip(drive + turn + strafe, -1.0, 1.0) * 0.8;
            frontRightPower = Range.clip(drive - turn - strafe, -1.0, 1.0) * 0.8;
            backLeftPower = Range.clip(drive + turn - strafe, -1.0, 1.0) * 0.8;
            backRightPower = Range.clip(drive - turn + strafe, -1.0, 1.0) * 0.8;

            //make sure left and right power are outside threshold


            // public double clip(double number, double min, double max)

/*           telemetry.addData("drive", drive);
            telemetry.addData("turn", turn);
            telemetry.addData("strafe", strafe);
            telemetry.addData("force", force);
            telemetry.addData("spin", spin);
            telemetry.addData("intakeSetting", intakeSetting);
            telemetry.addData("spinnerSetting", spinnerSetting);*/
            //Telemetry
//            telemetry.addData("encoder-front-left", FrontLeft.getCurrentPosition());
//            telemetry.addData("encoder-back-left", BackLeft.getCurrentPosition());
//            telemetry.addData("encoder-front-right", FrontRight.getCurrentPosition());
//            telemetry.addData("encoder-back-right", BackRight.getCurrentPosition());
            telemetry.addData("Claw Position", clawPos);
            telemetry.addData("Arm Position", armPos);

            telemetry.update();
            //Sets all of the motors
            Arm.setPosition(armPos);
            Claw.setPosition(clawPos);
            FrontLeft.setPower(multiplier * frontLeftPower);
            FrontRight.setPower(multiplier * frontRightPower);
            BackLeft.setPower(multiplier * backLeftPower);
            BackRight.setPower(multiplier * backRightPower);
            Intake.setPower(intakeFactor * intakePower);
            Spinner.setPower(spinFactor * spinnerPower);
            Intake2.setPower(-intakeFactor * intakePower);
            Slide.setPower(slidePower);
        }
        telemetry.update();
    }
}
