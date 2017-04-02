package test.test3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CountryView extends Stage {

    private CountryController controller;
    private CountriesModel model;
    private ObjectProperty<Country> selectedCountry;

    public CountryView(final CountriesModel model, final CountryController controller) {
        this.model = model;
        this.controller = controller;
        this.selectedCountry = new SimpleObjectProperty<>(controller.getSelectedCountryByIndex(CountriesModel.DEFAULT_COUNTRY));

        this.setScene(new Scene(new VBox(
            createListView(),
            createInputField(),
            createCountryInfo()
        )));
        this.show();
    }

    private ListView createListView() {
        ListView<Country> countryListView = new ListView<>();
        countryListView.setItems(FXCollections.observableList(model.getCountries()));

        countryListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectedCountry.set(countryListView.getSelectionModel().getSelectedItem())
        );

        return countryListView;
    }

    private TextField createInputField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter #country");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                selectedCountry.set(controller.getSelectedCountryByIndex(Long.parseLong(newValue)));
            } catch (NumberFormatException e) {
                selectedCountry.set(controller.getSelectedCountryByIndex(CountriesModel.DEFAULT_COUNTRY));
            }
        });

        return textField;
    }

    private GridPane createCountryInfo() {
        Label countryInfoName = new Label("Country(* = all):");
        Label countryInfoPopulation = new Label("Population:");
        Label countryInfoArea = new Label("Area:");
        Label countryInfoPopulationDensity = new Label("Population Density:");

        Label countryInfoNameValue = new Label();
        countryInfoNameValue.textProperty().bind(
            Bindings.createStringBinding(() -> selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                    model.getDefaultName() :
                    selectedCountry.get().getName(),
            selectedCountry)
        );

        Label countryInfoPopulationValue = new Label();
        countryInfoPopulationValue.textProperty().bind(
            Bindings.createStringBinding(() -> Long.toString(selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getTotalPopulation() :
                selectedCountry.get().getPopulation()),
            selectedCountry)
        );

        Label countryInfoAreaValue = new Label();
        countryInfoAreaValue.textProperty().bind(
            Bindings.createStringBinding(() -> Long.toString(selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getTotalArea() :
                selectedCountry.get().getArea()),
            selectedCountry)
        );

        Label countryInfoPopulationDensityValue = new Label();
        countryInfoPopulationDensityValue.textProperty().bind(
            Bindings.createStringBinding(() -> String.format("%.3f", selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getTotalPopulationDensity() :
                selectedCountry.get().getPopulationDensity()),
            selectedCountry)
        );

        GridPane countryInfo = new GridPane();
        countryInfo.addRow(0, countryInfoName, countryInfoNameValue);
        countryInfo.addRow(1, countryInfoPopulation, countryInfoPopulationValue);
        countryInfo.addRow(2, countryInfoArea, countryInfoAreaValue);
        countryInfo.addRow(3, countryInfoPopulationDensity, countryInfoPopulationDensityValue);

        return countryInfo;
    }
}
