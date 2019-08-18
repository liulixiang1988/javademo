package io.liulx.concurrency;

public class CoarseSync {
    public static StringBuffer copyString100Times(String target) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            sb.append(target)
        }
        return sb;
    }
}
