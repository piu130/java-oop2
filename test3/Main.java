package test.test3;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CountriesModel model = FileManager.doFileConversion();

        new CountryView(model, new CountryController(model));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
