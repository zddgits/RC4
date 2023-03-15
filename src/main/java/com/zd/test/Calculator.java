package com.zd.test;

public class Calculator {
    public int add(int x, int y) {
        return x + y;
    }
    public int minus(int x, int y) {
        return x - y;
    }
    public int multiply(int x, int y) {
        return x * y;
    }
    public int divide(int x, int y) throws IllegalNumException {
        if(y == 0) throw new IllegalNumException("非法数字");
        return x / y;
    }
}
