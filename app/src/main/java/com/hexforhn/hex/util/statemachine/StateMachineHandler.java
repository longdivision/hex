package com.hexforhn.hex.util.statemachine;

public interface StateMachineHandler<State> {
    void onEnterState(State state);
}
