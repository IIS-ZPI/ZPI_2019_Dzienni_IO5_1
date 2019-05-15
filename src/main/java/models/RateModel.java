package models;

public class RateModel {

    private String no;
    private String effectiveDate;
    private Double mid;
    private Double bid;
    private Double ask;

    public RateModel(String no, String effectiveDate, Double mid, Double bid, Double ask) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.mid = mid;
        this.bid = bid;
        this.ask = ask;
    }

    public RateModel() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }
}
