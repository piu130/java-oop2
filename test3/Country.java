package test.test3;

public class Country {

    private long index;
    private String name;
    private long population;
    private long area;

    public Country() {
        this.index = CountriesModel.DEFAULT_COUNTRY;
        this.name = "*";
        this.population = 0;
        this.area = 0;
    }

    public Country(long index, String name, long population, long area) {
        this.index = index;
        this.name = name;
        this.population = population;
        this.area = area;
    }

    public long getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public long getArea() {
        return area;
    }

    public long getPopulation() {
        return population;
    }

    public double getPopulationDensity() {
        return area == 0 ? 0 : population / (double)area;
    }

    @Override
    public String toString() {
        return index + ":\t" + name;
    }
}
