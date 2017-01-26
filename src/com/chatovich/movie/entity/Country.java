package com.chatovich.movie.entity;

/**
 * Class that represents entity "country" (where a film was produced)
 */
public class Country extends Entity{

    private String countryName;

    public Country(long id, String countryName) {
        super(id);
        this.countryName = countryName;
    }

    public Country(){}

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString(){
        return countryName;
    }
}
