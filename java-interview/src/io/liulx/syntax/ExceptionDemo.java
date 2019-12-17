package io.liulx.syntax;

public class ExceptionDemo {
    public static int f(int n) {
        try {
            int r = n*n;
            return r;
        } finally {
            if (n == 2) return 0; //会覆盖原有try里的返回值
        }
    }
}
