package test.test3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountriesModel implements ConvertFromFiles {

    public static final long DEFAULT_COUNTRY = 0;

    private List<Country> countries = new ArrayList<>();

    public String getDefaultName() {
        return "*";
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void addCountry(final Country country) {
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

    @Override
    public void convert(final File... files) {
        try (
            Scanner popIn = new Scanner(new FileInputStream(files[0])).useDelimiter("\n");
            Scanner areaIn = new Scanner(new FileInputStream(files[1])).useDelimiter("\n")
        ) {
            int inc = 1;
            this.addCountry(new Country());

            while (popIn.hasNext()) {
                String[] country1 = popIn.next().split("\t");
                String[] country2 = areaIn.next().split("\t");

                String name;
                long population;
                long area;
                try {
                    name = country1[0].trim();
                    if(name.equals("")) {
                        name = country2[0].trim();
                        if(name.equals("")) throw new NumberFormatException();
                    }
                    population = Long.parseLong(country1[1].trim());
                    area = Long.parseLong(country2[1].trim());
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    continue;
                }

                this.addCountry(new Country(inc++, name, population, area));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
