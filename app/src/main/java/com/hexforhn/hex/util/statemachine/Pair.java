package com.hexforhn.hex.util.statemachine;

// android.util.Pair was faring strangely under test - the fields would instantly become null after
// construction
public class Pair<F, S> {
    public final F first;
    public final S second;
    public Pair(F f, S s) {
        first = f;
        second = s;
    }
}
