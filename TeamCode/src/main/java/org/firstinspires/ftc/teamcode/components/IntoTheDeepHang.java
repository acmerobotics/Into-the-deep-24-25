package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;

public class IntoTheDeepHang {
    private DcMotor hang;

    public void reachLowRung() {

    }

    public void hangLowRung() {

    }

    public void reachHighRung() {

    }

    public void hangHighRung() {

    }

    public void retractHang () {

    }


    public void powerOff(){
        this.hang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.hang.setPower(0.0);
    }
}
