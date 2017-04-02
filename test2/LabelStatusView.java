package test.test2;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LabelStatusView extends Stage {

    public LabelStatusView(final CounterController controller) {
        String running = "Watches are running";
        String stopped = "Watches are not running";
        Label label = new Label(controller.isRunning() ? running : stopped);

        controller.runningProperty().addListener((observable, oldValue, newValue) ->
            label.setText(newValue ? running : stopped)
        );

        Scene scene = new Scene(new HBox(label));

        this.setWidth(220.0);
        this.setScene(scene);
        this.show();
    }

    @Override
    public void hide() {
        Platform.exit();
    }
}
