package com.globalLogic.structure.response;

public class PhoneRequest {
    private long number;
    private int citycode;
    private String countrycode;

    public PhoneRequest(int number, int citycode, String conutrycode) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = conutrycode;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCitycode() {
        return citycode;
    }

    public void setCitycode(int citycode) {
        this.citycode = citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}
