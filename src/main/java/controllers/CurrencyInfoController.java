package controllers;

import models.CountSessionStateModel;
import models.CurrencyModel;
import models.RateModel;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URL;

//TODO: remove. implemented in CurrencyInfoLoader
public class CurrencyInfoController {
    private CurrencyModel currentCurrencyModel;
    private static double epsilon = 0.005;

    // last to ilosc dni w tył.
    // countSessionStateModel zawiera wynik.
    public CurrencyInfoController(String tableType, String currencySign, String last) {
        String urlBase = buildStringUrl(tableType, currencySign, last);
        currentCurrencyModel = createCurrencyModelFromUrl(urlBase);
        CountSessionStateModel countSessionStateModel = countSessionStateForThePeriod(currentCurrencyModel);
        // System.out.println(countSessionStateModel);
    }

    public static CurrencyModel createCurrencyModelFromUrl(String urlBase) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (CurrencyModel) mapper.readValue(new URL(urlBase), CurrencyModel.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String buildStringUrl(String tableType, String currencySign, String last) {
        String urlBase = "http://api.nbp.pl/api/exchangerates/rates/";
        urlBase += tableType + "/";
        urlBase += currencySign + "/";
        urlBase += "/last/" + last + "/";
        urlBase += "?format=json";
        return urlBase;
    }

    //TODO: wynieść do klasy z logiką biznesową?
    public static CountSessionStateModel countSessionStateForThePeriod(CurrencyModel currentCurrencyModel) {

        RateModel lastRateModel = null;
        CountSessionStateModel countSessionStateModel = new CountSessionStateModel();
        boolean firstRateModelIsFilled = false;

        for (RateModel r : currentCurrencyModel.getRates()) {
            if (!firstRateModelIsFilled) {
                lastRateModel = r;
                firstRateModelIsFilled = true;
            } else {
                if (Math.abs(r.getMid() - lastRateModel.getMid()) < epsilon) {
                    countSessionStateModel.increaseMaintainedResult();
                } else if (r.getMid() > lastRateModel.getMid()) {
                    countSessionStateModel.increaseGrownResult();
                } else if (r.getMid() < lastRateModel.getMid()) {
                    countSessionStateModel.increaseFellResult();
                }
                lastRateModel = r;
            }
        }
        return countSessionStateModel;
    }
}
