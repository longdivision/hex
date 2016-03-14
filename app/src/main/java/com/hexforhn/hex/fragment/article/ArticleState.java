package com.hexforhn.hex.fragment.article;

import com.hexforhn.hex.util.statemachine.StateMachine;
import com.hexforhn.hex.util.statemachine.StateMachineHandler;

public class ArticleState implements StateMachineHandler<ArticleState.State> {
    public enum State {
        INACTIVE, LOADING_URL, URL_UNAVAILABLE, LOADING_CONTENT, CONTENT_UNAVAILABLE, CONTENT_LOADED
    }
    public enum Event {
        URL_REQUESTED, URL_PROVIDED, URL_UNAVAILABLE, LOAD_REQUESTED, LOAD_SUCCEEDED, LOAD_FAILED,
        CLOSED
    }

    private ArticleStateHandler mHandler;
    private StateMachine<State, Event> mStateMachine;

    public ArticleState(ArticleStateHandler handler) {
        mHandler = handler;
        mStateMachine = new StateMachine<>(this, State.INACTIVE);
        mStateMachine.addTransition(Event.URL_REQUESTED, State.INACTIVE, State.LOADING_URL);
        mStateMachine.addTransition(Event.URL_PROVIDED, State.LOADING_URL, State.LOADING_CONTENT);
        mStateMachine.addTransition(Event.URL_PROVIDED, State.URL_UNAVAILABLE, State.LOADING_CONTENT);
        mStateMachine.addTransition(Event.URL_UNAVAILABLE, State.LOADING_URL, State.URL_UNAVAILABLE);
        mStateMachine.addTransition(Event.LOAD_REQUESTED, State.URL_UNAVAILABLE, State.LOADING_URL);
        mStateMachine.addTransition(Event.LOAD_REQUESTED, State.CONTENT_UNAVAILABLE, State.LOADING_CONTENT);
        mStateMachine.addTransition(Event.LOAD_REQUESTED, State.CONTENT_LOADED, State.LOADING_CONTENT);
        mStateMachine.addTransition(Event.LOAD_SUCCEEDED, State.LOADING_CONTENT, State.CONTENT_LOADED);
        mStateMachine.addTransition(Event.LOAD_FAILED, State.LOADING_CONTENT, State.CONTENT_UNAVAILABLE);
        mStateMachine.addTransition(Event.CLOSED, State.LOADING_URL, State.INACTIVE);
        mStateMachine.addTransition(Event.CLOSED, State.URL_UNAVAILABLE, State.INACTIVE);
        mStateMachine.addTransition(Event.CLOSED, State.LOADING_CONTENT, State.INACTIVE);
        mStateMachine.addTransition(Event.CLOSED, State.CONTENT_UNAVAILABLE, State.INACTIVE);
        mStateMachine.addTransition(Event.CLOSED, State.CONTENT_LOADED, State.INACTIVE);
    }

    public boolean sendEvent(Event event) {
        return mStateMachine.sendEvent(event);
    }

    @Override
    public void onEnterState(State state) {
        switch(state) {
            case LOADING_URL:
                mHandler.onEnterLoadingUrl();
                break;
            case LOADING_CONTENT:
                mHandler.onEnterLoadingContent();
                break;
            case URL_UNAVAILABLE:
                mHandler.onEnterUrlUnavailable();
                break;
            case CONTENT_UNAVAILABLE:
                mHandler.onEnterContentUnavailable();
                break;
            case CONTENT_LOADED:
                mHandler.onEnterContentLoaded();
                break;
        }
    }
}

