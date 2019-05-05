package controllers;

import models.CurrencyModel;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URL;

public class CurrencyInfoController {
    private CurrencyModel currentCurrencyModel;

    public CurrencyInfoController(String tableType, String currencySign, String last) {
        String urlBase = buildStringUrl(tableType, currencySign, last);
        currentCurrencyModel = createCurrencyModelFromUrl(urlBase);
        System.out.println(currentCurrencyModel.getRates().get(0).getMid());
    }

    private CurrencyModel createCurrencyModelFromUrl(String urlBase) {
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
    
}
