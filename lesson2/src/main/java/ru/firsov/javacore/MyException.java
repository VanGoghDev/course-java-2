package ru.firsov.javacore;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyException) {
            return getMessage().equals(((MyException) obj).getMessage());
        }
        return false;
    }
}
