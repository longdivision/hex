package com.hexfornh.hex.util;

import com.hexforhn.hex.util.statemachine.StateMachine;
import com.hexforhn.hex.util.statemachine.StateMachineHandler;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StateMachineTest {
    private enum State { STATE_A, STATE_B }
    private enum Event { EVENT_A, EVENT_B }
    private class Handler implements  StateMachineHandler<State> {
        public int eventCount = 0;
        public State stateToCount;
        @Override
        public void onEnterState(State state) {
            if (state.equals(stateToCount)) {
                eventCount += 1;
            }
        }
    }

    @Test
    public void canBeProvidedWithInitialState() {
        StateMachine<State, Event> stateMachine = new StateMachine<>(new Handler(), State.STATE_A);

        assertEquals(stateMachine.getState(), State.STATE_A);
    }

    @Test
    public void canAddTransition() {
        StateMachine<State, Event> stateMachine = new StateMachine<>(new Handler(), State.STATE_A);
        boolean transitionAdded = stateMachine.addTransition(Event.EVENT_A, State.STATE_A,
                State.STATE_B);

        assertEquals(transitionAdded, true);
    }

    @Test
    public void willTransitionRegisteredTransition() {
        StateMachine<State, Event> stateMachine = new StateMachine<>(new Handler(), State.STATE_A);
        stateMachine.addTransition(Event.EVENT_A, State.STATE_A, State.STATE_B);
        stateMachine.sendEvent(Event.EVENT_A);

        assertEquals(stateMachine.getState(), State.STATE_B);
    }

    @Test
    public void willNotTransitionForUnknownEvent() {
        StateMachine<State, Event> stateMachine = new StateMachine<>(new Handler(), State.STATE_A);
        stateMachine.addTransition(Event.EVENT_B, State.STATE_A, State.STATE_B);
        stateMachine.sendEvent(Event.EVENT_A);

        assertEquals(stateMachine.getState(), State.STATE_A);
    }

    @Test
    public void callsHandlerWhenAllowedTransitionOccurs() {
        Handler handler = new Handler();
        handler.stateToCount = State.STATE_B;
        StateMachine<State, Event> stateMachine = new StateMachine<>(handler, State.STATE_A);
        stateMachine.addTransition(Event.EVENT_A, State.STATE_A, State.STATE_B);

        assertEquals(handler.eventCount, 0);

        stateMachine.sendEvent(Event.EVENT_A);

        assertEquals(handler.eventCount, 1);
    }

    @Test
    public void doesNotCallsHandlerWhenDeniedTransitionOccurs() {
        Handler handler = new Handler();
        handler.stateToCount = State.STATE_B;
        StateMachine<State, Event> stateMachine = new StateMachine<>(handler, State.STATE_A);
        stateMachine.addTransition(Event.EVENT_A, State.STATE_A, State.STATE_B);

        assertEquals(handler.eventCount, 0);

        stateMachine.sendEvent(Event.EVENT_B);

        assertEquals(handler.eventCount, 0);
    }
}
