package controllers;

import Network.CurrencyInfoLoader;
import models.CurrencyModel;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.time.Period;

// Demo controller, to refactor or deleted
public class StatisticsController {
    private DescriptiveStatistics stats;
    private Period chosedPeriod;
    private CurrencyModel model;
    private String tableType = "a";
    private String currency = "usd";

    public StatisticsController() {
        stats = new DescriptiveStatistics();
    }

    public void setPeriodAndCalculate(String s) {
        switch (s) {
            case ("1 tydzien"):
                chosedPeriod = Period.of(0, 0, 7);
                break;
            case ("2 tygodnie"):
                chosedPeriod = Period.of(0, 0, 14);
                break;
            case ("1 miesiac"):
                chosedPeriod = Period.of(0, 1, 0);
                break;
            case ("kwartal"):
                chosedPeriod = Period.of(0, 3, 0);
                break;
            case ("pol roku"):
                chosedPeriod = Period.of(0, 6, 0);
                break;
            case ("rok"):
                chosedPeriod = Period.of(1, 0, 0);
                break;
        }

        // limit 93 dni na jedno zapytanie. wieksze zapytania rozbija na mniejsze i pobiera dane
        model = CurrencyInfoLoader.getInstance().getCurrencyModelForPeriod(tableType, currency, chosedPeriod);

        for (int i = 0; i < model.getRates().size(); i++) {
            stats.addValue(model.getRates().get(i).getMid());
        }
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //to zawiera wszystkie dane statystyczne, zwraca stringa ze wszystkim
    public DescriptiveStatistics getStats() {
        return this.stats;
    }
}
