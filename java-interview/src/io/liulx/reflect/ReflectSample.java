package io.liulx.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectSample {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        Class rc = Class.forName("io.liulx.reflect.Robot");

        //创建实例
        Robot r = (Robot) rc.newInstance();

        System.out.println("Class name is " + rc.getName());

        //Class.getDeclaredMethod能获取私有方法，但是获取不了继承的方法
        Method getHello = rc.getDeclaredMethod("throwHello", String.class);
        getHello.setAccessible(true);
        Object str = getHello.invoke(r, "Bob");
        System.out.println("getHello result is " + str);

        //Class.getMethod可以获取继承的方法，但是获取不了继承的方法
        Method sayHi = rc.getMethod("sayHi", String.class);
        sayHi.invoke(r, "Welcome");

        Field name = rc.getDeclaredField("name");
        name.setAccessible(true);
        name.set(r, "Alice");
        sayHi.invoke(r, "Welcome");
    }
}
