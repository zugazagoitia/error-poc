package javalin;

import io.javalin.Javalin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static final List<byte[]> list = new LinkedList<>();
    public static void main(String[] args) {

        Javalin app = Javalin.create();

        /*
         * Some code filling the stack
         */
        app.get("/error",ctx -> {
            int index = 1;

            while (true) {
                byte[] b = new byte[10 * 1024 * 1024]; // 10MB byte object
                list.add(b);
                Runtime rt = Runtime.getRuntime();
                System.out.printf("[%3s] Available heap memory: %s%n", index++, rt.freeMemory());
            }
        });

        /*
         * Download a 10MB file from the server.
         */
        app.get("/",ctx -> {
            byte[] b = new byte[10 * 1024 * 1024];
            Arrays.fill(b,(byte)7);
            ctx.result(b);
        });

        /*
         * Might work after filling the stack
         */
        app.get("/helloWorld",ctx -> {
           ctx.result("Hello World!");
        });

        /*
         * App status, might work, but the stack is full
         */
        app.get("/isAlive", ctx -> {
            ctx.result("yes");
        });

        app.start(8080);
    }
}