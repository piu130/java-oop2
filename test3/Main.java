package test.test3;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CountriesModel model = doFileConversion();

        new CountryView(model, new CountryController(model));
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static CountriesModel doFileConversion() {

        FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("DAT Files", "*.dat");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All Files", "*.*");

        File popFile = FileManager.askForFile("Choose file with population", datFilter, allFilter);
        File areaFile = FileManager.askForFile("Choose file with area", datFilter, allFilter);

        if(popFile == null || areaFile == null) System.exit(0);

        FileManager.convertFiles('/', '\t', ".txt", popFile, areaFile);

        return convertFilesToCountriesModel(popFile, areaFile);
    }

    public static CountriesModel convertFilesToCountriesModel(File populationFile, File areaFile) {
        CountriesModel model = null;

        try (
            Scanner popIn = new Scanner(new FileInputStream(new File(populationFile + ".txt"))).useDelimiter("\n");
            Scanner areaIn = new Scanner(new FileInputStream(new File(areaFile + ".txt"))).useDelimiter("\n")
        ) {
            int inc = 1;
            model = new CountriesModel();
            model.addCountry(new Country());

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

                model.addCountry(new Country(inc++, name, population, area));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }
}
