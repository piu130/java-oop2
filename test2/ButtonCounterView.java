package test.test2;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ButtonCounterView extends Stage {

    public ButtonCounterView(final CounterController controller) {
        Button startButton = new Button("Start");
        startButton.disableProperty().bind(controller.runningProperty());
        startButton.setOnAction(e -> controller.start());

        Button stopButton = new Button("Stop");
        stopButton.disableProperty().bind(controller.runningProperty().not());
        stopButton.setOnAction(e -> controller.stop());

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> controller.reset());

        Scene scene = new Scene(new HBox(
            startButton,
            stopButton,
            resetButton
        ));

        this.setWidth(220.0);
        this.setScene(scene);
        this.show();
    }

    @Override
    public void hide() {
        Platform.exit();
    }
}
