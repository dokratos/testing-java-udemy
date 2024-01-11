package org.testing;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrdered {

    StringBuilder completed = new StringBuilder("");

    @AfterEach
    void afterEach() {
        System.out.println("The state of the class is " + completed);
    }

    @Order(4)
    @Test
    void testD() {
        completed.append("4");
        System.out.println("running test D");
    }

    @Order(2)
    @Test
    void testB() {
        completed.append("2");
        System.out.println("running test B");
    }

    @Order(3)
    @Test
    void testC() {
        completed.append("3");
        System.out.println("running test C");
    }

    @Order(1)
    @Test
    void testA() {
        completed.append("1");
        System.out.println("running test A");
    }
}
