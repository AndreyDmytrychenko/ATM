package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

class InfoCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle("info", Locale.ENGLISH);
    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString("before"));
        AtomicBoolean hasMoney = new AtomicBoolean(false);
        CurrencyManipulatorFactory.getAllCurrencyManipulators().forEach(m -> {
                hasMoney.set(m.hasMoney());
                if (hasMoney.get()) {
                    ConsoleHelper.writeMessage(m.getCurrencyCode() + " - " + m.getTotalAmount());
                }

        });
        if (!hasMoney.get()) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
