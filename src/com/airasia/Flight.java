package com.airasia;

/**
 *
 * @author CHRIS
 */
public class Flight {

    private String departTime;
    private String arriveTime;
    private String codeFlight;
    private String duration;
    private String priceLF;
    private String pricePF;

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getCodeFlight() {
        return codeFlight;
    }

    public void setCodeFlight(String codeFlight) {
        this.codeFlight = codeFlight;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPriceLF() {
        return priceLF;
    }

    public void setPriceLF(String priceLF) {
        this.priceLF = priceLF;
    }

    public String getPricePF() {
        return pricePF;
    }

    public void setPricePF(String pricePF) {
        this.pricePF = pricePF;
    }

    @Override
    public String toString() {
        return "Flight [ DepartTime : " + departTime +  " , ArriveTime : " + arriveTime + " , CodeFlight : " + codeFlight + " , Duration : " + duration +  " , LowFare : " + priceLF + " , PremiumFlex : " + pricePF + " ] ";
    }
}
