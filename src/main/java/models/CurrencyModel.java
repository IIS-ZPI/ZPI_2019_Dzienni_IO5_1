package models;

import java.util.ArrayList;
import java.util.List;

public class CurrencyModel {

    private String table;
    private String currency;
    private String code;
    private List<RateModel> rates = null;

    public CurrencyModel(String table, String currency, String code, List<RateModel> rates) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }

    public CurrencyModel() {
        rates = new ArrayList<>();
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RateModel> getRates() {
        return rates;
    }

    public void setRates(List<RateModel> rates) {
        this.rates = rates;
    }
}
