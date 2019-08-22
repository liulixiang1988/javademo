package io.liulx.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LoadDifference {
    public static void main(String[] args) throws ClassNotFoundException {
        //ClassLoader cl = Robot.class.getClassLoader();
        Class r = Class.forName("io.liulx.reflect.Robot");
        Method[] methods = r.getMethods();
        for (Method m : methods) {
            System.out.println(m.getDeclaringClass());
        }

        Field[] fields = r.getFields();
        for (Field f : fields) {
            System.out.println(f.getDeclaringClass());
        }
    }
}
