package ru.firsov.javacore;

public class MyArraySizeException extends MyException {
    private final int length;

    public MyArraySizeException(String s, int length) {
        super(s);
        this.length = length;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " array size = " + length;
    }
}