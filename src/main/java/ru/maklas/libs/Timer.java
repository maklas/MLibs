package ru.maklas.libs;


/**
 * Created by maklas on 19.09.2017.
 */

public class Timer {

    private boolean triggered = false;
    private float timer = 0;
    private float time = 0;
    private Action action;


    public Timer() {

    }

    public Timer(float seconds, Action action) {
        this.action = action;
        this.time = seconds;
    }

    public Timer setAction(Action action){
        this.action = action;
        return this;
    }

    public Timer setTime(float seconds){
        this.time = seconds;
        return this;
    }

    /**
     * Resets time to 0 and enables if Action returned true last time
     */
    public Timer reset(){
        timer = 0;
        triggered = false;
        return this;
    }

    /**
     * Updates timer. Returns true if Action was triggered
     */
    public boolean update(float dt){
        if (!triggered){
            timer+=dt;

            if (timer > time){
                triggered = true;
                if (action != null){
                    if (action.execute()){
                        reset();
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Action getAction() {
        return action;
    }

    /**
     * Action that's going to be triggered later in time
     */
    public interface Action {

        /**
         * @return whether we should reset timer and lauch again
         */
        boolean execute();
    }

}
