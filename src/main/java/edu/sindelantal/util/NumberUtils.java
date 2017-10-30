package edu.sindelantal.util;

import java.util.Random;

public class NumberUtils {

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt( (max - min) + 1 ) + min;
    }

    public static Long randomLong(){
        Random random = new Random();
        return Math.abs(random.nextLong());
    }

}
