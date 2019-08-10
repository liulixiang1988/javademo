package io.liulx.jvm.model;

public class Helloworld {
    private String name;

    public void sayHello() {
        System.out.println("Hell " + name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        int a = 1;
        Helloworld hw = new Helloworld();
        hw.setName("test");
        hw.sayHello();
    }
}
