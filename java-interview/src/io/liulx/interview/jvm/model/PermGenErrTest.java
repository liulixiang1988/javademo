package io.liulx.interview.jvm.model;

import java.util.Random;

public class PermGenErrTest {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            genRandomString(1000000).intern();
        }
        System.out.printf("Mission Complete!!!");
    }

    private static String genRandomString(int length) {
        String str = "abcdefghijklmnopqistuvwxyzABCDEFGHIJKLMNOPQISTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int nextIndex = random.nextInt(62);
            sb.append(str.charAt(nextIndex));
        }
        return sb.toString();
    }
}
