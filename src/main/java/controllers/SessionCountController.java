package controllers;

import models.CountSessionStateModel;
import models.CurrencyModel;
import models.RateModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SessionCountController {
    //TODO refactor
    enum Change {
        DECREASE, MAINTAIN, INCREASE, UNKNOWN
    }

    CurrencyModel currencyModel;
    RateModel lastRateModel;
    CountSessionStateModel countSessionStateModel;
    boolean foundFriday;
    boolean isDesiredChangeSet;
    double change;
    Change currentChange;
    Change desiredChange;
    LocalDate lastFriday;
    boolean doItAnyway;

    private static final double epsilon = 0.005;

    public SessionCountController(CurrencyModel currencyModel) {
        this.currencyModel = currencyModel;
        this.countSessionStateModel = new CountSessionStateModel();
        this.foundFriday = false;
        this.isDesiredChangeSet = false;
        this.doItAnyway = false;
        this.desiredChange = Change.UNKNOWN;
    }

    public CountSessionStateModel calculateSessionCount(){

        int i = findPreviousFridayIndex( this.currencyModel.getRates().size() - 1 );
        if (i == -1)
            /*TODO throw exception because there is no friday*/;

        this.lastRateModel = this.currencyModel.getRates().get(i);

        for (i-- ; i >= 0; i--) {
            LocalDate currentDate = LocalDate.parse(this.currencyModel.getRates().get(i).getEffectiveDate());
            DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
            if ( ( currentDayOfWeek.equals(DayOfWeek.FRIDAY) )  || Math.abs( ChronoUnit.DAYS.between(lastFriday, currentDate) ) > 7 || this.doItAnyway ) {
                change = lastRateModel.getMid() - this.currencyModel.getRates().get(i).getMid();
                if (Math.abs(change) < epsilon) {
                    currentChange = Change.MAINTAIN;
                    if (!isDesiredChangeSet){
                        desiredChange = Change.MAINTAIN;
                        isDesiredChangeSet = true;
                        countSessionStateModel.increaseMaintainedResult();
                    } else if (currentChange == desiredChange) {
                        countSessionStateModel.increaseMaintainedResult();
                    } else
                        break;
                } else if (change < 0) {
                    currentChange = Change.DECREASE;
                    if (!isDesiredChangeSet){
                        desiredChange = Change.DECREASE;
                        isDesiredChangeSet = true;
                        countSessionStateModel.increaseFellResult();
                    } else if (currentChange == desiredChange) {
                        countSessionStateModel.increaseFellResult();
                    } else
                        break;
                } else if (change > 0) {
                    currentChange = Change.INCREASE;
                    if (!isDesiredChangeSet){
                        desiredChange = Change.INCREASE;
                        isDesiredChangeSet = true;
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

                if (!(currentDayOfWeek.equals(DayOfWeek.FRIDAY))) {
                    this.foundFriday = false;
                    i = findPreviousFridayIndex(i);
                    if (i == -1)
                        /*TODO throw exception because there is no friday*/;
                    this.doItAnyway = true;
                }
            }
        }
        return countSessionStateModel;
    }

    protected int findPreviousFridayIndex(int i){
        for ( ; i >= 0; i--) {
            LocalDate currentDate = LocalDate.parse(this.currencyModel.getRates().get(i).getEffectiveDate());
            DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
            if ((currentDayOfWeek.equals(DayOfWeek.FRIDAY))) {
                this.lastFriday = currentDate;
                this.foundFriday = true;
                return i;
            }
        }
        return -1;
    }

}
