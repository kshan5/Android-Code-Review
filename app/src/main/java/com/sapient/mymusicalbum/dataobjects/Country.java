package com.sapient.mymusicalbum.dataobjects;

/**
 * Created by kshan5 on 1/12/2017.
 */

public class Country {

    private String countryAbb;
    private String countryName;


    public Country(String countryAbb, String countryName) {
        this.countryAbb = countryAbb;
        this.countryName = countryName;
    }

    public String getCountryAbb() {
        return countryAbb;
    }

    public void setCountryAbb(String countryAbb) {
        this.countryAbb = countryAbb;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    @Override
    public String toString() {
        return countryName;
    }
}
