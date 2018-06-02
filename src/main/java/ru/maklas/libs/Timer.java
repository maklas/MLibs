package ru.maklas.libs;


/**
 * Created by maklas on 19.09.2017.
 * Timer counts up from 0 to desired updateRate and triggers acton when updateRate has come
 */

public class Timer {

    private boolean enabled = true;
    private boolean looped;
    private float currentTime;
    private float updateRate;
    private Action action;

    /**
     * Creates new looped Timer with update rate of 1 second and no action to trigger
     */
    public Timer() {
        this.updateRate = 1f;
        this.looped = true;
    }

    /**
     * @param updateRate in seconds. How many times to trigger this action
     * @param action will be enabled every updateRate is finished
     */
    public Timer(float updateRate, boolean looped, Action action) {
        this.action = action;
        this.updateRate = updateRate;
        this.looped = looped;
    }

    public Timer setAction(Action action){
        this.action = action;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public Timer setUpdateRate(float seconds){
        this.updateRate = seconds;
        return this;
    }

    public float getUpdateRate() {
        return updateRate;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isLooped() {
        return looped;
    }

    public void setLooped(boolean looped) {
        this.looped = looped;
    }

    /**
     * Whether this timer is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Resets current time to 0 and enables Timer
     */
    public void reset(){
        currentTime = 0;
        enabled = true;
    }

    /**
     * Updates currentTime. Returns true if Action was enabled
     */
    public void update(float dt){
        if (!enabled) return;

        currentTime += dt;
        if (currentTime > updateRate){
            currentTime -= updateRate;
            if (action != null) action.execute();
            if (!looped) enabled = false;
        }
    }

    /**
     * Action that's going to be enabled later in updateRate
     */
    public interface Action {

        void execute();
    }

}
