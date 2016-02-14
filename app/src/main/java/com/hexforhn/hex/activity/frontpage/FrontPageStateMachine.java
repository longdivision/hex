package com.hexforhn.hex.activity.frontpage;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontPageStateMachine {
    public enum State {
        INACTIVE,
        ITEMS_LOADING,
        ITEMS_LOADED,
        ITEMS_UNAVAILABLE,
        REFRESHING
    }
    public enum Transition {
        LOAD_REQUESTED,
        LOAD_SUCCEEDED,
        LOAD_FAILED,
        REFRESH_REQUESTED,
        REFRESH_SUCCEEDED,
        REFRESH_FAILED
    }
    private final Map<State, List<Pair<State, Transition>>> mTransitionTable;
    private final FrontPageStateHandler mHandler;
    private State mState;
    private static final String TAG = FrontPageStateMachine.class.getName();
    private boolean mLogDebugOutput;

    public FrontPageStateMachine(FrontPageStateHandler handler, boolean logDebugOuput) {
        mLogDebugOutput = logDebugOuput;
        mTransitionTable = new HashMap<>();

        mTransitionTable.put(State.INACTIVE, new ArrayList<Pair<State, Transition>>(){{
            add(new Pair(State.ITEMS_LOADING, Transition.LOAD_REQUESTED));
        }});
        mTransitionTable.put(State.ITEMS_LOADING, new ArrayList<Pair<State, Transition>>(){{
            add(new Pair(State.ITEMS_LOADED, Transition.LOAD_SUCCEEDED));
            add(new Pair(State.ITEMS_UNAVAILABLE, Transition.LOAD_FAILED));
        }});
        mTransitionTable.put(State.ITEMS_LOADED, new ArrayList<Pair<State, Transition>>(){{
            add(new Pair(State.REFRESHING, Transition.REFRESH_REQUESTED));
        }});
        mTransitionTable.put(State.ITEMS_UNAVAILABLE, new ArrayList<Pair<State, Transition>>(){{
            add(new Pair(State.REFRESHING, Transition.REFRESH_REQUESTED));
        }});
        mTransitionTable.put(State.REFRESHING, new ArrayList<Pair<State, Transition>>(){{
            add(new Pair(State.ITEMS_UNAVAILABLE, Transition.REFRESH_FAILED));
            add(new Pair(State.ITEMS_LOADED, Transition.REFRESH_SUCCEEDED));
        }});

        mState = State.INACTIVE;
        mHandler = handler;
    }

    public boolean transitionTo(State newState) {
        List<Pair<State, Transition>> availableTransitions = mTransitionTable.get(mState);

        for(Pair<State, Transition> availableTransition : availableTransitions ) {
            if (availableTransition.first.equals(newState)) {
                mState = availableTransition.first;
                invokeTransition(availableTransition.second);
                logSuccessfulTransition(mState, newState, availableTransition.second);
                return true;
            }
        }

        logFailedTransitionError(mState, newState);
        return false;
    }

    private void invokeTransition(Transition transition) {
        switch(transition) {
            case LOAD_REQUESTED:
                mHandler.onLoadRequested();
                break;
            case LOAD_SUCCEEDED:
                mHandler.onLoadSucceeded();
                break;
            case LOAD_FAILED:
                mHandler.onLoadFailed();
                break;
            case REFRESH_REQUESTED:
                mHandler.onRefreshRequested();
                break;
            case REFRESH_SUCCEEDED:
                mHandler.onRefreshSucceeded();
                break;
            case REFRESH_FAILED:
                mHandler.onRefreshFailed();
                break;
        }
    }

    private void logSuccessfulTransition(State from, State to, Transition transition) {
        if (!mLogDebugOutput) { return; }

        Log.e(TAG, "Transitioned from " + from.toString() + " to " + to.toString() +
                "(" + transition.toString() + ")");
    }

    private void logFailedTransitionError(State from, State to) {
        if (!mLogDebugOutput) { return; }

        Log.e(TAG, "Unable to transition from " + from.toString() + " to " + to.toString());
        Log.e(TAG, Thread.currentThread().getStackTrace().toString());
    }

}
