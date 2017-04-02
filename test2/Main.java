package test.test2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    // File name to (de)serialize to and from
    public static final String SERIALIZE_FILE = "stopWatch.ser";

    // Controller to serialize
    private CounterController controller;

    @Override
    public void start(Stage stage) {
        CounterModel model;

        // Deserialize controller with model if available else create new instances
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZE_FILE))) {
            controller = (CounterController) ois.readObject();
            model = controller.getModel();
        } catch (IOException | ClassNotFoundException e) {
            model = new CounterModel();
            controller = new CounterController(model);
        }

        new CounterView(model, controller);
        new LabelStatusView(controller);
        new LabelCounterView(model);
        new ButtonCounterView(controller);
    }

    @Override
    public void stop() {
        // Serialize controller (with model)
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZE_FILE))) {
            oos.writeObject(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
