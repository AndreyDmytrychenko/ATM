package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CurrencyManipulator {
    private String currencyCode;
    public Map<Integer, Integer> denominations;


    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        this.denominations = new TreeMap<>();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, count + denominations.get(denomination));
        } else {
            denominations.put(denomination, count);
        }

    }

    public int getTotalAmount() {
        AtomicInteger res = new AtomicInteger(0);
        denominations.forEach( (k, v) -> {
            res.addAndGet(k * v);
        });
        return res.get();
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public  Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> res = new HashMap<>();

        int sum = expectedAmount;
        List<Integer> Q = mapToSortList(denominations);
        int i, k, v = 0;
        for(i = 0; i < Q.size(); i++) {
            k = Q.get(i);
            if (i > 0 && k != Q.get(i-1)) {
                if (v > 0) {
                    res.put(Q.get(i-1), v);
                }
                v = 0;
                if (sum == 0) break;
            }
            if (sum == 0) {
                if (v > 0) {
                    res.put(Q.get(i-1), v);
                }
                break;
            }
            if (i < Q.size() - 1 && k > sum) continue;
            sum -= k;
            if (sum < 0) throw new NotEnoughMoneyException();
            v++;
            if (i == Q.size() - 1 && v > 0) {
                res.put(k, v);
            }
        }
        updateDenomination(res);
        return res;
    }

    private List<Integer> mapToSortList(Map<Integer, Integer> map) {
        List<Integer> list = new ArrayList<>();

        map.forEach( (k, v) -> {
            for (int i = 0; i < v; i++) {
                list.add(k);
            }
        });
        list.sort( (v1,v2) -> Integer.compare(v2,v1));
        return list;
    }

    public void updateDenomination(Map<Integer,Integer> withdrawAmount) {
        withdrawAmount.forEach( (k, v) -> {
            int newVal = denominations.get(k) - v;
            if (newVal == 0) {
                denominations.remove(k);
            } else {
                denominations.put(k, newVal);
            }
        });
    }
}
