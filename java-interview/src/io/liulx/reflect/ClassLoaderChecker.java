package io.liulx.reflect;

public class ClassLoaderChecker {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        MyClassLoader m = new MyClassLoader("d:/Project/java/javabasic/ClassLoaderDemo/", "myClassLoader");
        Class c = m.findClass("Wali");
        System.out.println(c.getClassLoader());
        System.out.println(c.getClassLoader().getParent());
        System.out.println(c.getClassLoader().getParent().getParent());
        System.out.println(c.getClassLoader().getParent().getParent().getParent());
        c.newInstance();
    }
}
