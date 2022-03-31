package fr.unantes.sce.calendar;

import java.util.Objects;

/**
 * A city
 */
public class City {
    public String country;
    public String name;

    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Test if two cities are equals (same name, same country)
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return getCountry().equals(city.getCountry()) &&
                getName().equals(city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getName());
    }
}
