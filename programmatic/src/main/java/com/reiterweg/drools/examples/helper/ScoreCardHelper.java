package com.reiterweg.drools.examples.helper;

public final class ScoreCardHelper {

    public static Long generate() {
        return Double.valueOf(Math.random() * 10000000.0).longValue();
    }

}
