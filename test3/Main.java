package test.test3;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        File[] files = convertFiles();

        CountriesModel model = new CountriesModel();
        model.convert(files[0], files[1]);

        new CountryView(model, new CountryController(model));
    }

    private File[] convertFiles() {
        FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("DAT Files", "*.dat");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All Files", "*.*");

        File popFile = FileManager.askForFile("Choose file with population", datFilter, allFilter);
        File areaFile = FileManager.askForFile("Choose file with area", datFilter, allFilter);

        if(popFile == null || areaFile == null) System.exit(0);

        FileManager.convertFiles('/', '\t', ".txt", popFile, areaFile);

        return new File[]{ popFile, areaFile };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
