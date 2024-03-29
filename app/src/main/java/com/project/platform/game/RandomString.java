package com.project.platform.game;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase (Locale.ROOT);
    private static final String digits = "0123456789";
    private static final String alphanumeric = upper + lower + digits;
    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    private RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException ();
        if (symbols.length () < 2) throw new IllegalArgumentException ();
        this.random = Objects.requireNonNull (random);
        this.symbols = symbols.toCharArray ();
        this.buf = new char[length];
    }

    private RandomString(int length, Random random) {
        this (length, random, alphanumeric);
    }

    private RandomString(int length) {
        this (length, new SecureRandom ());
    }

    public RandomString() {
        this (16);
    }

    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt (symbols.length)];
        return new String (buf);
    }

}