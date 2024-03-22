package com.cykj.util;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/28 0:35
 */
public class ServerConsoleUtils {
    public static final String GREEN = "\33[32;1m";
    public static final String RED = "\33[31;1m";

    public static void printOut(Object message, String title, String color) {
        System.out.print(color + "[" + title + "] " + message.toString() + "\33[0m" + "\n");
    }

    public static void printOut(Object message, String color) {
        System.out.print(color + "[Server] " + message.toString() + "\33[0m" + "\n");
    }

    public static void printOut(Object message) {
        printOut(message.toString(), GREEN);
    }
}
