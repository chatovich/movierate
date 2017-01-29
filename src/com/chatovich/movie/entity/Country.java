package com.chatovich.movie.entity;

import java.util.Objects;

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
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass()!=o.getClass()) return false;
        Country country = (Country) o;
        return super.getId()==country.getId()&&
                Objects.equals(countryName, country.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName, super.getId());
    }
}
