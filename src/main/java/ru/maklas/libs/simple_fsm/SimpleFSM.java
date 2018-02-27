package ru.maklas.libs.simple_fsm;

/**
 * Created by maklas on 03-Feb-18.
 */

public class SimpleFSM<E extends Enum<E>> {

    private final SimpleState<E>[] stateMap;
    private E currentStateEnum;
    private SimpleState<E> currentState;
    private E futureState = null;
    private boolean isUpdating = false;

    public SimpleFSM(Class<E> clazz) {
        stateMap = (SimpleState<E>[]) new SimpleState[clazz.getEnumConstants().length];
    }

    public SimpleFSM<E> set(SimpleState<E> state){
        int ordinal = state.getEnum().ordinal();
        stateMap[ordinal] = state;
        state.addedToFSM(this);
        return this;
    }

    public void start(E state){
        currentStateEnum = state;
        currentState = stateMap[state.ordinal()];
        switchStateLocal(state);
    }

    public void update(float dt){
        if (currentStateEnum != null){
            isUpdating = true;
            currentState.update(dt);
            isUpdating = false;
        }
        if (futureState != null){
            switchStateLocal(futureState);
            futureState = null;
        }
    }

    public void dispose(){
        for (SimpleState<E> state : stateMap) {
            if (state != null){
                state.dispose();
            }
        }

    }

    public final boolean isUpdating(){
        return isUpdating;
    }

    public final boolean isSwitchingState(){
        return futureState != null;
    }

    public final E getFutureState(){
        return futureState;
    }

    public final boolean hasStarted(){
        return currentStateEnum != null;
    }

    public void changeState(E state){
        if (isUpdating){
            futureState = state;
        } else {
            switchStateLocal(state);
        }
    }

    public E getState(){
        return currentStateEnum;
    }

    public SimpleState<E> getSimpleState(){
        return currentState;
    }

    public SimpleState<E> getSimpleState(E state){
        return stateMap[state.ordinal()];
    }

    private void switchStateLocal(E state){
        E previousStateEnum = currentStateEnum;
        SimpleState<E> newState = stateMap[state.ordinal()];
        currentState.onExitState(state);
        this.currentStateEnum = state;
        currentState = newState;
        currentState.onEnterState(previousStateEnum);
    }

}
