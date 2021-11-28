package com.javarush.task.task26.task2613;

public enum Operation {
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        return switch (i) {

            case 1 -> INFO;
            case 2 -> DEPOSIT;
            case 3 -> WITHDRAW;
            case 4 -> EXIT;
            default -> throw new IllegalArgumentException();
        };
    }
}
