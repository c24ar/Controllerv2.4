package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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
    double drive;
    double turn;
    double strafe;
    double force;
    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;
    double intakePower;
    boolean protectionMode;
    double multiplier;
    int intakeSetting;
    double intakeFactor;
    boolean fullShutdown;
    boolean partyMode;
    int i;
    boolean trackingMode;
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
        frontLeftPower = 0.0;
        frontRightPower = 0.0;
        backLeftPower = 0.0;
        backRightPower = 0.0;
        intakePower = 0.0;
        protectionMode = true;
        multiplier = 0.25;
        intakeSetting = 1;
        intakeFactor = 1.0;
        fullShutdown = false;
        partyMode = false;
        i = 0;
        trackingMode = false;

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);


        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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

    public void loop() {
        if (!fullShutdown) {
                if (partyMode) {
                    int time = 0;
                    while (time < 500) {
                        if ((time >= 0 && time < 25) || (time >= 75 && time < 125)) {
                            drive = 0;
                            strafe = -1;
                            turn = 0;
                            time = time + 1;
                        }
                        if ((time >= 25 && time < 75) || (time >= 125 && time < 175)) {
                            drive = 0;
                            strafe = 1;
                            turn = 0;
                            time = time + 1;
                        }
                        if (time >= 175) {
                            drive = 0;
                            strafe = 0;
                            turn = 1;
                            time = time + 1;
                        }
                        drive = 0;
                        strafe = -gamepad1.left_stick_x;
                        turn = 0;
                        time = time + 1;
                        frontLeftPower = Range.clip(drive + turn + strafe, -1.0, 1.0) * 0.8;
                        frontRightPower = Range.clip(drive - turn - strafe, -1.0, 1.0) * 0.8;
                        backLeftPower = Range.clip(drive + turn - strafe, -1.0, 1.0) * 0.8;
                        backRightPower = Range.clip(drive - turn + strafe, -1.0, 1.0) * 0.8;
                        FrontLeft.setPower(frontLeftPower);
                        FrontRight.setPower(frontRightPower);
                        BackLeft.setPower(backLeftPower);
                        BackRight.setPower(backRightPower);
                    }
                    partyMode = false;
                }
                if (!partyMode) {
                    if (gamepad1.b) {
                        double y_coordinate = -gamepad1.left_stick_x;
                        double x_coordinate = -gamepad1.left_stick_y;
                        telemetry.addLine("tracking mode on");
                        double distance = Math.sqrt(Math.pow(x_coordinate, 2) + Math.pow(y_coordinate, 2));
                        telemetry.addLine(String.valueOf(distance));
                        if (distance > 0.0) {
                            double angle = 50 * Math.asin(y_coordinate / distance);
                            telemetry.addLine(String.valueOf(angle));
                            int i = 0;
                            while (i < (5000 * Math.abs(angle * y_coordinate))) {
                                if (x_coordinate < 0) {
                                    frontLeftPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                                    frontRightPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                                    backLeftPower = Range.clip(-angle, -1.0, 1.0) * 0.8;
                                    backRightPower = Range.clip(angle, -1.0, 1.0) * 0.8;
                                    FrontLeft.setPower(0.5 * frontLeftPower);
                                    FrontRight.setPower(0.5 * frontRightPower);
                                    BackLeft.setPower(0.5 * backLeftPower);
                                    BackRight.setPower(0.5 * backRightPower);
                                    i++;
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
                                    i++;
                                }
                            }
                            int j = 0;
                            while (j < (50000 * distance)) {
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
                                Intake.setPower(intakePower);
                                k++;
                            }
                            Intake.setPower(0);
                        }
                    } else {
                        if (gamepad1.a && !gamepad1.y) {
                            protectionMode = false;
                            multiplier = 1.0;
                        }
                        if (gamepad1.a && gamepad1.y) {
                            protectionMode = true;
                            multiplier = 0.25;
                        }
                        if (gamepad1.dpad_down && gamepad1.x) {
                            fullShutdown = true;
                        }
                        if (gamepad1.x && !gamepad1.dpad_down) {
                            intakeSetting = intakeSetting + 1;
                            if (intakeSetting > 6) {
                                intakeSetting = 1;
                            }
                        }
                        if (gamepad1.y && !gamepad1.a) {
                            partyMode = true;
                        }
                        if (intakeSetting == 1) {
                            intakeFactor = 1.0;
                        }
                        if (intakeSetting == 2) {
                            intakeFactor = 0.6;
                        }
                        if (intakeSetting == 3) {
                            intakeFactor = 0.2;
                        }
                        if (intakeSetting == 4) {
                            intakeFactor = -0.2;
                        }
                        if (intakeSetting == 5) {
                            intakeFactor = -0.6;
                        }
                        if (intakeSetting == 6) {
                            intakeFactor = -1.0;
                        }

                        //Forward/backward and strafing with the left stick, turning with the right

                        force = (gamepad1.right_trigger + gamepad1.left_trigger) / 2;
                        drive = gamepad1.left_stick_y;
                        strafe = -gamepad1.left_stick_x;
                        turn = -gamepad1.right_stick_x;
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
                        telemetry.update();
                        FrontLeft.setPower(multiplier * frontLeftPower);
                        FrontRight.setPower(multiplier * frontRightPower);
                        BackLeft.setPower(multiplier * backLeftPower);
                        BackRight.setPower(multiplier * backRightPower);
                        Intake.setPower(intakeFactor * intakePower);


                        // Show the elapsed game time
                        telemetry.addData("Status", "Run Time: " + runtime.toString());
                    }
                    telemetry.update();
                }
            }
    }
}