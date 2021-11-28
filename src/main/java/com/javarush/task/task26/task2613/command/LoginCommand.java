package com.javarush.task.task26.task2613.command;


import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command{

    private ResourceBundle validCreditCards = ResourceBundle.getBundle("verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle("login", Locale.ENGLISH);
    private String cardNumber;
    private String pinCode;

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        ConsoleHelper.writeMessage(res.getString("specify.data"));


        ConsoleHelper.writeMessage(res.getString("card.data"));
        while (!isCardNumberTrue(cardNumber = ConsoleHelper.readString())){
            ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format.card"), cardNumber));
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format.card"), cardNumber));

        ConsoleHelper.writeMessage(res.getString("pin.data"));
        while (!isPinCodeTrue(pinCode = ConsoleHelper.readString())){
            ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format.pin"), pinCode));
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format.pin"), pinCode));
    }
    private boolean isCardNumberTrue(String data) {
        return validCreditCards.containsKey(data);
    }

    private boolean isPinCodeTrue(String data) {
       return validCreditCards.getString(cardNumber).equals(data);
    }



}
