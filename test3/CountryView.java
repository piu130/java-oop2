package test.test3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    private Node createListView() {
        ListView<Country> countryListView = new ListView<>();
        countryListView.setItems(FXCollections.observableList(model.getCountries()));

        countryListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectedCountry.set(countryListView.getSelectionModel().getSelectedItem())
        );

        return countryListView;
    }

    private Node createInputField() {
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

    private Node createCountryInfo() {
        return new VBox(
            createCountryInfoName(),
            createCountryInfoPopulation(),
            createCountryInfoArea(),
            createCountryInfoPopulationDensity()
        );
    }

    private Node createCountryInfoName() {
        Label text = new Label("Country(* = all):");

        Label value = new Label();
        value.textProperty().bind(
            Bindings.createStringBinding(() -> selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getDefaultName() :
                selectedCountry.get().getName(),
            selectedCountry)
        );

        HBox hBox = new HBox(text, value);
        hBox.setSpacing(23);
        return hBox;
    }

    private Node createCountryInfoPopulation() {
        Label text = new Label("Population:");

        Label value = new Label();
        value.textProperty().bind(
            Bindings.createStringBinding(() -> Long.toString(selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getTotalPopulation() :
                selectedCountry.get().getPopulation()),
            selectedCountry)
        );

        HBox hBox = new HBox(text, value);
        hBox.setSpacing(45);
        return hBox;
    }

    private Node createCountryInfoArea() {
        Label text = new Label("Area:");

        Label value = new Label();
        value.textProperty().bind(
            Bindings.createStringBinding(() -> Long.toString(selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getTotalArea() :
                selectedCountry.get().getArea()),
            selectedCountry)
        );

        HBox hBox = new HBox(text, value);
        hBox.setSpacing(79);
        return hBox;
    }

    private Node createCountryInfoPopulationDensity() {
        Label text = new Label("Population Density:");

        Label value = new Label();
        value.textProperty().bind(
            Bindings.createStringBinding(() -> String.format("%.3f", selectedCountry.get().getIndex() == CountriesModel.DEFAULT_COUNTRY ?
                model.getTotalPopulationDensity() :
                selectedCountry.get().getPopulationDensity()),
            selectedCountry)
        );

        HBox hBox = new HBox(text, value);
        hBox.setSpacing(3);
        return hBox;
    }
}
