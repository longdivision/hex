package com.hexforhn.hex.util.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine<State, Event> {

    private Map<Event, List<Pair<State, State>>> mTransitionTable;
    private State mState;
    private StateMachineHandler mHandler;

    public StateMachine(StateMachineHandler handler, State initialState) {
        mHandler = handler;
        mState = initialState;
        mTransitionTable = new HashMap<>();
    }

    public boolean addTransition(Event event, State source, State target) {
        if (!mTransitionTable.containsKey(event)) {
            mTransitionTable.put(event, new ArrayList<Pair<State, State>>());
        }
        return mTransitionTable.get(event).add(new Pair<>(source, target));
    }

    public boolean sendEvent(Event event) {
        List<Pair<State, State>> possibleTransitions = mTransitionTable.get(event);

        if (possibleTransitions == null) {
            return false;
        }

        for(Pair<State, State> possibleTransition: possibleTransitions) {
            if (mState.equals(possibleTransition.first)) {
                mState = possibleTransition.second;
                enteringState(mState);
                return true;
            }
        }

        return false;
    }

    public State getState() {
        return mState;
    }

    private void enteringState(State state) {
        mHandler.onEnterState(state);
    }
}
