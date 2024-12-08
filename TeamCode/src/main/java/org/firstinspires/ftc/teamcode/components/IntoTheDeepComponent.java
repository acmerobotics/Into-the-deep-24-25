package org.firstinspires.ftc.teamcode.components;

public interface IntoTheDeepComponent {

    void init();

    default void sleep (int ms){
        try{
            Thread.sleep(ms);
        }catch (InterruptedException ie){

        }
    }
}
