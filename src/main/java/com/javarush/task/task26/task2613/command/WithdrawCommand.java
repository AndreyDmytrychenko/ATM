package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class WithdrawCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle("withdraw", Locale.ENGLISH);
    private String currencyCode;
    @Override
    public void execute() throws InterruptOperationException {

          CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode = ConsoleHelper.askCurrencyCode());


        while (true) {
            try {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));

                String sum = ConsoleHelper.readString();
                while (correctSum(sum) == null || !currencyManipulator.isAmountAvailable(Integer.parseInt(sum))) {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                    sum = ConsoleHelper.readString();
                }
                Map<Integer,Integer> map = currencyManipulator.withdrawAmount(Integer.parseInt(sum));
                //sort map and print
                sortMapAndPrint(map);

                ConsoleHelper.writeMessage(res.getString("exact.amount"));
                break;

            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
    }

    private void sortMapAndPrint(Map<Integer, Integer> map) {
        ConsoleHelper.writeMessage(res.getString("before"));
        List<Integer> list = map.keySet().stream().sorted( (v1, v2) -> Integer.compare(v2,v1)).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"),
                    list.get(i), map.get(list.get(i)), currencyCode));
        }
    }

    private String correctSum(String data) {
        Matcher m = Pattern.compile("^\\s*([1-9][0-9]*)\\s*$").matcher(data);
        String sum = null;
        if (m.find()) {
            sum = m.group(1);

        }
        return sum;
    }
}
