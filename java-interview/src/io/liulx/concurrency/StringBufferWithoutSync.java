package io.liulx.concurrency;

public class StringBufferWithoutSync {
    public String add(String a1, String a2) {
        //StringBuffer是线程安全的，由于sb只会在append方法中使用，不可能被其他线程引用，
        //因此sb属于不可能共享的资源，JVM会自动消除内部的锁
        StringBuffer sb = new StringBuffer();
        sb.append(a1);
        sb.append(a2);
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBufferWithoutSync withoutSync = new StringBufferWithoutSync();
        for (int i = 0; i < 100; i++) {
            withoutSync.add("aaa", "bbb");
        }
    }
}
