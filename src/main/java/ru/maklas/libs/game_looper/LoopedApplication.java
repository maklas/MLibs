package ru.maklas.libs.game_looper;

/**
 * Created by maklas on 19.08.2017.
 */

public interface LoopedApplication {

    void onStart(Looper looper);

    void update(float dt);

    void dispose();

}
