package test.test3;

import java.util.ArrayList;
import java.util.List;

public class CountriesModel {

    public static final long DEFAULT_COUNTRY = 0;

    private List<Country> countries = new ArrayList<>();

    public String getDefaultName() {
        return "*";
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public long getTotalPopulation() {
        return countries.parallelStream().mapToLong(Country::getPopulation).sum();
    }

    public long getTotalArea() {
        return countries.parallelStream().mapToLong(Country::getArea).sum();
    }

    public double getTotalPopulationDensity() {
        long totalArea = getTotalArea();
        return totalArea == 0 ? 0 : getTotalPopulation() / totalArea;
    }
}
