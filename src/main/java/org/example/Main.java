package org.example;

import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = 1;
        float y = 2;
        float z = Util.add(x, y);
        float[] f = { 3, 2, 1 };
        for (int i = 0; i < f.length; i++) {
            f[i] = Util.add(z, i);
            if (f[i] == 12) break;
        }
        Instant now = Instant.now();
        now.toEpochMilli();
        System.out.println("Hello world " + Util.add(z, y) + " " + Util.add(z, z) + " " + Arrays.toString(f));

        Scanner scn = new Scanner(System.in);
        System.out.print("Enter name: ");
        // take input
        String name = scn.nextLine();
        System.out.println("Name: " + name);
        scn.close();
    }

    static int switchEx(int[] input) throws Exception {
        switch (input.length) {
            case 2:
            case 3:
            case 4:
                return Util.add(input[0], input[1]);
            default:
                throw new Exception("Input length unexpected");
        }
    }
}

