package io.liulx.syntax;

public class GenericDemo {
}

class Pair<T> {
    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}

class ArrayAlg{
    public static <T> T getMiddle(T... a) {
        return a[a.length/2];
    }

    public static <T extends Comparable> T min(T[] a) {

    }
}
