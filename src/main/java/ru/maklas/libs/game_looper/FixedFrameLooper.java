package ru.maklas.libs.game_looper;

/**
 * Created by maklas on 19.08.2017.
 */

public class FixedFrameLooper extends Thread implements Looper {

    private static final float SEC_TO_NANO = 1000000000f;
    private final double loopTimeMillis;
    private volatile boolean isRunning = true;
    private LoopedApplication app;
    private final Queue<Runnable> threadExecutionQueue;

    public FixedFrameLooper(LoopedApplication app, int fps) {
        super();
        this.app = app;
        this.fps = fps;
        this.threadExecutionQueue = new Queue<Runnable>();

        double dt = 1d/fps;
        this.loopTimeMillis = dt * 1000;
    }

    public FixedFrameLooper(LoopedApplication app) {
        this(app, 60);
    }

    @Override
    public void run() {

        LoopedApplication app = this.app;
        double loopTimeMillis = this.loopTimeMillis;

        app.onStart(this);

        double previous = System.nanoTime() - 0.016 * 1000000000;

        while (isRunning){

            executeQueuedTasks();

            final double current = System.nanoTime();
            final double elapsed = current - previous;
            previous = current;
            final float delta = (float)(elapsed / SEC_TO_NANO);
            app.update(delta);
            updateFrameRate();

            final double testCurrent = System.nanoTime();
            final double updateTimeNano = testCurrent - current;
            final double updateTimeMillis = updateTimeNano / 1000000;
            long toWaitMillis = (long)(loopTimeMillis - updateTimeMillis);
            if (toWaitMillis < 0){
                toWaitMillis = 0;
            }
            //waiting
            try {
                Thread.sleep(toWaitMillis);
            } catch (InterruptedException e){
                isRunning = false;
                break;
            }
        }

        app.dispose();
        this.app = null;

        executeQueuedTasks();
        synchronized (threadExecutionQueue) {
            threadExecutionQueue.clear();
        }
    }

    private void executeQueuedTasks() {
        synchronized (threadExecutionQueue) {
            Queue<Runnable> queue = this.threadExecutionQueue;
            Runnable command = queue.removeFirst();
            while (command != null) {
                command.run();
                command = queue.removeFirst();
            }
        }
    }


    private int frameCounter = 1;
    private long lastTime = 0;
    private float fps = 0;
    private void updateFrameRate(){
        frameCounter++;

        if (frameCounter % 60 == 0){
            long nano = System.nanoTime();
            long diff = nano - lastTime;
            fps = 60 / (diff/SEC_TO_NANO);
            lastTime = nano;
        }
    }


    /**
     * Lets current loop to finish and stops execution of run()
     */
    public void stopExecution() {
        isRunning = false;
    }


    @Override
    public float getFps(){
        return fps;
    }

    @Override
    public void exit() {
        stopExecution();
    }

    @Override
    public boolean isExiting() {
        return !isRunning;
    }

    @Override
    public void postRunnable(Runnable runnable){
        synchronized (threadExecutionQueue) {
            threadExecutionQueue.addLast(runnable);
        }
    }
}
