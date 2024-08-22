package org.util;

@SuppressWarnings("unused")
public class Fn {
    public static int add(int a, int b) { return a + b; }
    public static float add(int a, float b) { return a + b; }
    public static float add(float a, float b) { return a + b; }

    public static void printColor(AnsiColors color, String text) {
        System.out.println(color.getValue() + text + AnsiColors.RESET.getValue());
    }
}
