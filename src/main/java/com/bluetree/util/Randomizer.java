package com.bluetree.util;

import java.util.Random;

public class Randomizer {

    private Random random;

    public Randomizer() {
        random = new Random();
    }

    public int[] keys(int length, int max) {
        int[] keys = new int[length];
        int i = 0;
        while (i < length) {
            keys[i] = random.nextInt(max);
            for (int j = 0; keys[i] != -1 && j < i; j++) {
                if (keys[j] == keys[i]) {
                    keys[i] = -1;
                }
            }
            if (keys[i] != -1) {
                i++;
            }
        }
        return keys;
    }

}