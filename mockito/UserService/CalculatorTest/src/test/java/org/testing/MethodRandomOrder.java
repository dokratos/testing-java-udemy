package org.testing;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class MethodRandomOrder {
    @Test
    void testA() {
        System.out.println("running test A");
    }

    @Test
    void testB() {
        System.out.println("running test B");
    }

    @Test
    void testC() {
        System.out.println("running test C");
    }

    @Test
    void testD() {
        System.out.println("running test D");
    }
}
