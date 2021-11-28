package com.javarush.task.task26.task2613;
import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;


public class CashMachine {
    public static void main(String[] args) {


        try {
            Operation operation = Operation.LOGIN;
            CommandExecutor.execute(operation);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);

            } while (operation != Operation.EXIT);
        } catch (InterruptOperationException ignored) {}

    }
}
