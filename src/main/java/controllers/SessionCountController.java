package controllers;

import Network.CurrencyInfoLoader;
import models.CountSessionStateModel;
import models.CurrencyModel;
import models.RateModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class SessionCountController {
    public CountSessionStateModel getCountSessionStateModel() {
        return countSessionStateModel;
    }

    //TODO refactor
    enum Change {
        DECREASE, MAINTAIN, INCREASE
    }

    private CurrencyModel currencyModel;
    private boolean foundFriday; //Used mainly when we skip friday for any reason e.g. because of holidays
    private LocalDate lastFriday; //Used to track if we skipped friday. We will check if there is more than 7 days between lastFriday and currentDate
    private String tableType = "a";
    private String currency = "usd";
    private Period period;
    private CountSessionStateModel countSessionStateModel;
    private DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;

    private static double epsilon = 0.005;

    public SessionCountController() {
        this.foundFriday = false;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public SessionCountController(CurrencyModel currencyModel) {
        this.currencyModel = currencyModel;
        this.foundFriday = false;
    }

    public CountSessionStateModel calculateSessionCount() throws NoFridayException{
        boolean doItAnyway = false; //If we skipped friday we have to compare rates between next friday and whatever day was closest to that skipped friday
        Change currentChange, desiredChange = null;
        RateModel lastRateModel;
        CountSessionStateModel countSessionStateModel = new CountSessionStateModel();

        int i = findPreviousFridayIndex(this.currencyModel.getRates().size() - 1); //If we're in the middle of session, then we're discarding those days and start calculating from first friday
        if (i == -1)
            throw new NoFridayException();

        lastRateModel = this.currencyModel.getRates().get(i);

        for (i--; i >= 0; i--) {
            LocalDate currentDate = LocalDate.parse(this.currencyModel.getRates().get(i).getEffectiveDate());
            DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
            if ((currentDayOfWeek.equals(dayOfWeek)) || Math.abs(ChronoUnit.DAYS.between(lastFriday, currentDate)) > 7 || doItAnyway) {
                double change = lastRateModel.getMid() - this.currencyModel.getRates().get(i).getMid();
                if (Math.abs(change) < epsilon) {
                    currentChange = Change.MAINTAIN;
                    if (desiredChange == null) {
                        desiredChange = Change.MAINTAIN;
                        countSessionStateModel.increaseMaintainedResult();
                    } else if (currentChange == desiredChange) {
                        countSessionStateModel.increaseMaintainedResult();
                    } else
                        break;
                } else if (change < 0) {
                    currentChange = Change.DECREASE;
                    if (desiredChange == null) {
                        desiredChange = Change.DECREASE;
                        countSessionStateModel.increaseFellResult();
                    } else if (currentChange == desiredChange) {
                        countSessionStateModel.increaseFellResult();
                    } else
                        break;
                } else if (change > 0) {
                    currentChange = Change.INCREASE;
                    if (desiredChange == null) {
                        desiredChange = Change.INCREASE;
                        countSessionStateModel.increaseGrownResult();
                    } else if (currentChange == desiredChange) {
                        countSessionStateModel.increaseGrownResult();
                    } else
                        break;
                } else {
                    //Throw some kind of Exception
                }
                lastRateModel = this.currencyModel.getRates().get(i);
                lastFriday = currentDate;

                if (!(currentDayOfWeek.equals(dayOfWeek))) {
                    this.foundFriday = false;
                    i = findPreviousFridayIndex(i);
                    if (i == -1)
                        /*TODO throw exception because there is no friday*/ ;
                    doItAnyway = true;
                }
            }
        }
        return countSessionStateModel;
    }

    /*This method is used firstly in finding first friday and secondly when we for whatever reason skip fridays
     * TBH, I'm not sure if that second usage is required, will have to look into it later*/
    private int findPreviousFridayIndex(int i) {
        for (; i >= 0; i--) {
            LocalDate currentDate = LocalDate.parse(this.currencyModel.getRates().get(i).getEffectiveDate());
            DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
            if ((currentDayOfWeek.equals(dayOfWeek))) {
                this.lastFriday = currentDate;
                this.foundFriday = true;
                return i;
            }
        }
        return -1;
    }

    public void setPeriodAndCalculate(String s) {
        switch (s) {
            case ("1 tydzień"):
                period = Period.of(0, 0, 7);
                break;
            case ("2 tygodnie"):
                period = Period.of(0, 0, 14);
                break;
            case ("1 miesiac"):
                period = Period.of(0, 1, 0);
                break;
            case ("kwartal"):
                period = Period.of(0, 3, 0);
                break;
            case ("pół roku"):
                period = Period.of(0, 6, 0);
                break;
            case ("rok"):
                period = Period.of(1, 0, 0);
                break;
        }

        // limit 93 dni na jedno zapytanie. wieksze zapytania rozbija na mniejsze i pobiera dane

        currencyModel = CurrencyInfoLoader.getInstance().getCurrencyModelForPeriod(tableType, currency, period);
        if (currencyModel.getTable().equalsIgnoreCase("a")) {
            this.dayOfWeek = DayOfWeek.FRIDAY;
            this.epsilon = 0.005;
        } else {
            this.dayOfWeek = DayOfWeek.WEDNESDAY;
            this.epsilon = 0;
        }
        try {
            this.countSessionStateModel = calculateSessionCount();
        } catch (NoFridayException e) {
            this.countSessionStateModel = null;
        }
    }

    public class NoFridayException extends Exception {
        public NoFridayException(){
            super();
        }
    }
}
