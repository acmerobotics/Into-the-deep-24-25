package org.firstinspires.ftc.teamcode.autonomous;

public class IntoTheDeepConfiguration {

    private long delay;

    public void incrementDelay(long increment){
        this.delay = this.delay + increment;
    }

    public void decrementDelay(long decrement){
        if (delay > 0){
            this.delay = this.delay - decrement;
        }
    }
}
