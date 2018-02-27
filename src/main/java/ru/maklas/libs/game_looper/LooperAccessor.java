package ru.maklas.libs.game_looper;

/**
 * Created by amaklakov on 01.11.2017.
 */
public interface LooperAccessor {

    /**
     * Actual fps
     */
    float getFps();

    /**
     * Finishes frame and stops execution of looping thread
     */
    void exit();

    /**
     * Whether this GameLoop is exiting atm
     */
    boolean isExiting();

    /**
     * Runs this runnable under Looper thread before each {@link LoopedApplication#update(float)};
     */
    void postRunnable(Runnable runnable);

}
