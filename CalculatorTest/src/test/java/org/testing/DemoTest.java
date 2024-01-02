package org.testing;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math")
public class DemoTest {

    Calculator calculator;


//    must be static!!
    @BeforeAll
    static void setup() {
        System.out.println("Executing @BeforeAll");
    }

    //    must be static!!
    @AfterAll
    static void cleanup() {
        System.out.println("Executing @AfterAll");
    }

    @BeforeEach
    void beforeEach() {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach");
    }

    @AfterEach
    void after() {
        System.out.println("Running after each!");
    }

//    test Method _condition or state change_expected result ::
    @DisplayName("display name annotation")
    @Test
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {
        int result = calculator.integerDivision(4, 2);
        assertEquals(2, result, "4/2 must return 2");
    }

    @Test
    void subtraction() {
//        AAA
//        Arrange
        int minuend = 8;
        int subtrahend = 2;
        int expectedResult = 6;
//        Act
        int result = calculator.integerSubtraction(minuend, subtrahend);
//        Assert

        assertEquals(expectedResult, result, () -> minuend + " minus " + subtrahend + " must return 6");
    }

//    the error message will always be computed, even if the test passes. To avoid it, a lambda can be used!
    @Test
    void failSubtract() {
        int min = 3;
        int sub = 2;
        int result = calculator.integerSubtraction(min, sub);
        assertEquals(1, result, () ->min + " - "+ sub + " must return 1!");
    }

    @DisplayName("division by zero, failing test")
    @Test
    void testIntegerDivision_WhenDivisionIsByZero_ShouldThrowException() {
//        Arrange
        int dividend = 4;
        int divisor = 1;
//        Act & Assert
       ArithmeticException arit = assertThrows(ArithmeticException.class, () -> {
//            Act
            calculator.integerDivision(dividend, divisor);
        }, "Division by zero should throw exception");

//       Assert
        assertEquals("/ by zero", arit.getMessage(), " unexpected message");
    }


    @Disabled("The test does not run, but is included in the test report")
    @DisplayName("Fail and disable")
    @Test
    void testFailingMustFail() {
        fail("Running a failing test");
    }
}
