package controllers;

import Network.CurrencyInfoLoader;
import models.CurrencyModel;
import models.RateModel;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class CurrencyPairController {
    private CurrencyInfoLoader currencyInfoLoader;
    private Period chosedPeriod;
    private String chosedCurrencyPair;
    private List<String> availableCurrencyPairs;

    public CurrencyPairController() {
        this.currencyInfoLoader = CurrencyInfoLoader.getInstance();
        availableCurrencyPairs = new ArrayList<>();
        addAvailablePairs();
    }

    public List<String> getAvailableCurrencyPairs() {
        return availableCurrencyPairs;
    }

    private void addAvailablePairs(){
        availableCurrencyPairs.add("EUR/USD");
        availableCurrencyPairs.add("EUR/GBP");
        availableCurrencyPairs.add("AUD/USD");
        availableCurrencyPairs.add("AUD/JPY");
        availableCurrencyPairs.add("USD/JPY");
        availableCurrencyPairs.add("USD/CHF");
        availableCurrencyPairs.add("GBP/USD");
    }

    public void setPeriod(String s) {
        switch (s) {
            case ("1 miesiac"):
                chosedPeriod = Period.of(0, 1, 0);
                break;
            case ("kwartal"):
                chosedPeriod = Period.of(0, 3, 0);
                break;
        }
    }

    public void setChosedCurrencyPair(String chosedCurrencyPair) {
        this.chosedCurrencyPair = chosedCurrencyPair;
    }

    public CurrencyModel calculateCurrencyPair() {

        String[] currencyPair = chosedCurrencyPair.split("/");
        CurrencyModel first = currencyInfoLoader.getCurrencyModelForPeriod("c", currencyPair[0], chosedPeriod);
        CurrencyModel second = currencyInfoLoader.getCurrencyModelForPeriod("c", currencyPair[1], chosedPeriod);
        CurrencyModel result = new CurrencyModel();

        for (int i = 0; i < first.getRates().size(); i++) {
            double value = first.getRates().get(i).getAsk() / second.getRates().get(i).getBid();
            RateModel temp = new RateModel(first.getRates().get(i).getEffectiveDate(), value);
            result.getRates().add(temp);
        }
        return result;
    }

}
