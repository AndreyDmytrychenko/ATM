package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

class DepositCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle("deposit", Locale.ENGLISH);
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);

        while (true) {
            try {
                    String[] data = ConsoleHelper.getValidTwoDigits();
                    int denomination = Integer.parseInt(data[0]);
                    int count = Integer.parseInt(data[1]);
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination*count, currencyCode));
                    currencyManipulator.addAmount(denomination, count);
                    break;
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }

    }
}