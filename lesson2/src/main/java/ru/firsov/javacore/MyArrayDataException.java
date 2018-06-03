package ru.firsov.javacore;

public class MyArrayDataException extends MyException {
    private final int i;
    private final int j;
    private final String val;

    public MyArrayDataException(String message, String val, int i, int j) {
        super(message);
        this.i = i;
        this.j = j;
        this.val = val;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + String.format(" value[%s][%s]=%s", i, j, val);
    }
}
