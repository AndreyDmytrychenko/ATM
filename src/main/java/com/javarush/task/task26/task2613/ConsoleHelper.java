package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleHelper {

    private static ResourceBundle res = ResourceBundle.getBundle("common", Locale.ENGLISH);

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String message = "";
        try {
            if (isExit(message = bis.readLine())) {
                ConsoleHelper.writeMessage(res.getString("the.end"));
                throw new InterruptOperationException();
            }

        } catch (IOException ignored) {}


        return message;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String currencyCode;
        while(!isLetters(currencyCode = readString())) {
            writeMessage(res.getString("invalid.data"));
        }

        return currencyCode.toUpperCase();
    }

    public static String[] getValidTwoDigits() throws InterruptOperationException {
        writeMessage(res.getString("choose.denomination.and.count"));

        String[] res = hasDigitsMoreZero(readString());

        if (res == null) {
            throw new NumberFormatException();
        }

        return res;
    }

    private static String[] hasDigitsMoreZero(String data) {
        Matcher m = Pattern.compile("^\\s*([1-9][0-9]*)\\s+([1-9][0-9]*)\\s*$").matcher(data);
        String[] res = null;
        if (m.find()) {
            res = new String[2];
            res[0] = m.group(1);
            res[1] = m.group(2);
        }
        return res;
    }

    private static boolean isExit(String data) {
        return Pattern.compile("EXIT").matcher(data.toUpperCase()).find();
    }

    private static boolean isLetters(String data) {
        return Pattern.compile("^[a-zA-Z]{3}$").matcher(data).find();
    }

    private static boolean isNumberOfOperation(String data) {
        return Pattern.compile("^[1-4]$").matcher(data).find();
    }


    public static Operation askOperation() throws InterruptOperationException {
        printAvailableOperation();
        String number;
        while(!isNumberOfOperation(number = readString())) {
            writeMessage(res.getString("invalid.data"));
            printAvailableOperation();
        }
        return Operation.getAllowableOperationByOrdinal(Integer.parseInt(number));
    }

    private static void printAvailableOperation() {
        ConsoleHelper.writeMessage(res.getString("choose.operation"));
        ConsoleHelper.writeMessage("\t1 - " + res.getString("operation.INFO"));
        ConsoleHelper.writeMessage("\t2 - " + res.getString("operation.DEPOSIT"));
        ConsoleHelper.writeMessage("\t3 - " + res.getString("operation.WITHDRAW"));
        ConsoleHelper.writeMessage("\t4 - " + res.getString("operation.EXIT"));
    }
}
