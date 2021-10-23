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
    double drive = 0.0;
    double turn = 0.0;
    double strafe = 0.0;
    double force = 0.0;
    double frontLeftPower = 0.0;
    double frontRightPower = 0.0;
    double backLeftPower = 0.0;
    double backRightPower = 0.0;
    double intakePower = 0.0;
    boolean unlocked = false;
    String password = "";
    double L = 0.0;
    double R = 0.0;
    boolean protectionMode = true;
    double multiplier = 0.25;
    int intakeSetting = 1;
    double intakeFactor = 1.0;
    boolean fullShutdown = false;
    boolean partyMode = false;
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
            if (unlocked) {
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
                    if (gamepad1.left_bumper && gamepad1.right_bumper) {
                        double y_coordinate = gamepad1.left_stick_y;
                        double x_coordinate = -gamepad1.left_stick_x;
                        telemetry.addLine("tracking mode on");
                        double distance = Math.sqrt(Math.pow(x_coordinate, 2) + Math.pow(y_coordinate, 2));
                        if (distance > 5) {
                            double angle = Math.asin((50 * y_coordinate) / distance);
                            int i = 0;
                            while (i < (50 * angle)) {
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
                            int j = 0;
                            while (j < (50 * distance)) {
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
                            int k = 0;
                            while (k < 75) {
                                intakePower = Range.clip(1.0, -1.0, 1.0) * 0.8;
                                Intake.setPower(intakePower);
                                k++;
                            }
                            distance = 0.0;
                            y_coordinate = 0.0;
                            x_coordinate = 0.0;
                        }
                    } else {
                        if (gamepad1.a && protectionMode) {
                            protectionMode = false;
                            multiplier = 1.0;
                        }
                        if (gamepad1.a && !protectionMode) {
                            protectionMode = true;
                            multiplier = 0.25;
                        }
                        if (gamepad1.options && gamepad1.b) {
                            fullShutdown = true;
                        }
                        if (gamepad1.x) {
                            intakeSetting = intakeSetting + 1;
                            if (intakeSetting > 6) {
                                intakeSetting = 1;
                            }
                        }
                        if (gamepad1.ps) {
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
            } else {
                L = gamepad1.left_trigger;
                R = gamepad1.right_trigger;
                if (L > 0.5) {
                    password = password + "L";
                }
                if (R > 0.5) {
                    password = password + "R";
                }
                if (password.length() >= 5) {
                    if (password.equals("RLRRL")) {
                        telemetry.addLine("unlocked");
                        telemetry.update();
                        unlocked = true;
                    } else {
                        password = "";
                        telemetry.addLine("Uh Uh Uh, you didn't say the magic word!");
                        telemetry.update();
                    }
                }
            }
        }
    }
}