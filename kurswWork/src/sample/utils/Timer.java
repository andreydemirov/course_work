package sample.utils;


public class Timer {
    private static int time;

    public Timer() {
        time = 0;
    }

    public static int getTime() {
        return time;
    }

    public static void incTime() {
        time++;
    }

    public static void zeroing() {
        time = 0;
    }
}
