package sample.utils;

import java.util.Random;

public class Utils {
    public static Random random = new Random();

    public static int getRandomNumber(int size) {
        return random.nextInt(size) + 1;
    }

    public static int getRandomNumber(int left, int right) {
        return left + random.nextInt(right - left + 1);
    }
}
