package ru.maklas.libs.simple_fsm;

/**
 * Created by maklas on 03-Feb-18.
 */

public abstract class SimpleState<E extends Enum<E>> {

    private final E state;
    private SimpleFSM<E> fsm;

    public SimpleState(E state) {
        this.state = state;
    }

    public final E getEnum() {
        return state;
    }

    final void addedToFSM(SimpleFSM<E> fsm) {
        this.fsm = fsm;
    }

    public abstract void onEnterState(E previous);

    public abstract void update(float dt);

    public abstract void onExitState(E next);

    protected final SimpleFSM<E> getFsm(){
        return fsm;
    }

    protected void changeState(E state){
        getFsm().changeState(state);
    }

    public final boolean isUpdating(){
        return getFsm().isUpdating() && getFsm().getState() == getEnum();
    }

    public final boolean isSwitchingState(){
        return getFsm().isSwitchingState();
    }


    public void dispose() {}
}
