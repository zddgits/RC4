package com.zd.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class test {
    public Calculator calculator = new Calculator();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Test
    public void testNum1() throws IllegalNumException{
        exception.expect(IllegalNumException.class);
        exception.expectMessage("非法数字");
        calculator.divide(5,0);
    }
}
