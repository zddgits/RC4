package com.zd.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    public Calculator calculator = new Calculator();

    @ParameterizedTest
    @CsvSource({"2,3,5","4,4,8"})
    void add(ArgumentsAccessor args) {
        assertEquals(args.getInteger(2),calculator.add(args.getInteger(0),args.getInteger(1)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/minusTest.csv")
    void minus(int x, int y, int sum) {
        assertEquals(sum, calculator.add(x, y));
    }

    @Test
    void multiply() {
        assertEquals(48,calculator.multiply(6,8));
    }

    @Test
    void divide() throws IllegalNumException{
        assertEquals(2,calculator.divide(4,2));
    }

    @Test
    void testNum() throws IllegalNumException{
        try {
            calculator.divide(5,0);
            fail("an illegalNumException is going to be thrown");
        } catch (IllegalNumException e) {
            assertEquals("非法数字", e.getMessage());
        }

    }
}