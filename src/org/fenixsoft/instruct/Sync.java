package org.fenixsoft.instruct;

public class Sync {
    void foo(String s) {
        synchronized (s) {
            System.out.println("executed");
        }
    }
}
