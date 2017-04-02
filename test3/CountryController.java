package test.test3;

public class CountryController {

    private CountriesModel model;

    public CountryController(final CountriesModel model) {
        this.model = model;
    }

    public Country getSelectedCountryByIndex(long index) {
        return model.getCountries()
                .parallelStream()
                .filter(country -> country.getIndex() == index)
                .findAny()
                .orElse(null);
    }
}
