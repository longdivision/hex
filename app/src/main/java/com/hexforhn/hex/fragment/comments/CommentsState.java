package com.hexforhn.hex.fragment.comments;

import com.hexforhn.hex.util.statemachine.StateMachine;
import com.hexforhn.hex.util.statemachine.StateMachineHandler;

public class CommentsState implements StateMachineHandler<CommentsState.State> {
    public enum State {
        LOADING, LOADED, UNAVAILABLE
    }
    public enum Event {
        COMMENTS_PROVIDED, COMMENTS_UNAVAILABLE, LOAD_REQUESTED
    }

    private CommentsStateHandler mHandler;
    private StateMachine<State, Event> mStateMachine;

    public CommentsState(CommentsStateHandler handler) {
        mHandler = handler;
        mStateMachine = new StateMachine<>(this, State.LOADING);
        mStateMachine.addTransition(Event.COMMENTS_PROVIDED, State.LOADING, State.LOADED);
        mStateMachine.addTransition(Event.COMMENTS_PROVIDED, State.LOADING, State.LOADED);
        mStateMachine.addTransition(Event.COMMENTS_PROVIDED, State.UNAVAILABLE, State.LOADED);
        mStateMachine.addTransition(Event.COMMENTS_UNAVAILABLE, State.LOADING, State.UNAVAILABLE);
        mStateMachine.addTransition(Event.LOAD_REQUESTED, State.UNAVAILABLE, State.LOADING);
        mStateMachine.addTransition(Event.LOAD_REQUESTED, State.LOADED, State.LOADING);
    }

    public boolean sendEvent(Event event) {
        return mStateMachine.sendEvent(event);
    }

    @Override
    public void onEnterState(State state) {
        switch(state) {
            case LOADING:
                mHandler.onEnterLoading();
                break;
            case LOADED:
                mHandler.onEnterLoaded();
                break;
            case UNAVAILABLE:
                mHandler.onEnterUnavailable();
                break;
        }
    }
}

