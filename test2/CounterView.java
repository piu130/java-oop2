package test.test2;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class CounterView extends Stage implements Observer {
    private CounterModel model;
    private Label label;

    public CounterView(CounterModel model, CounterController controller) {
        this.model = model;

        Button startButton = new Button("Start");
        startButton.disableProperty().bind(controller.runningProperty());
        startButton.setOnAction(e -> controller.start());

        Button stopButton = new Button("Stop");
        stopButton.disableProperty().bind(controller.runningProperty().not());
        stopButton.setOnAction(e -> controller.stop());

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> controller.reset());

        label = new Label();

        Scene scene = new Scene(new HBox(
                startButton,
                stopButton,
                resetButton,
                label
        ));

        model.addObserver(this);
        setTime(model.getValue());

        this.setWidth(220.0);
        this.setScene(scene);
        this.show();
    }

    public void update(Observable obs, Object args) {
        if(obs == model) setTime(model.getValue());
    }

    private void setTime(long seconds) {
        String time = String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
        label.setText(time);
    }

    @Override
    public void hide() {
        Platform.exit();
    }
}
