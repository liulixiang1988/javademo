package io.liulx.syntax;

import java.io.Console;

public class PasswordDemo {
    public static void main(String[] args) {
        Console console = System.console();
        String userName = console.readLine("username:");
        char[] passwd = console.readPassword("password:");
        System.out.println(userName);
        System.out.println(passwd);
    }
}
