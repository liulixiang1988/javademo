package io.liulx.syntax;

public class InnerClass {
    public static void main(String[] args) {
        A a = new A();
        A.B b = a.new B();
    }
}

class A {
    private int foo;

    void hello() {
        B b = new B();
        //或
        A.B bb = this.new B();
    }

    public class B {
        private int bar;

        public void say() {
            foo = 2;
            //或者
            A.this.foo = 2;
        }
    }
}

class C {
    void dd() {
        A a = new A();
        A.B b = a.new B();
    }
}
