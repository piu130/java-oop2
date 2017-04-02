package test.test2;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class LabelCounterView extends Stage implements Observer {
    private CounterModel model;
    private Label label;

    public LabelCounterView(final CounterModel model) {
        this.model = model;
        this.label = new Label();

        Scene scene = new Scene(new HBox(label));

        model.addObserver(this);
        setTime(model.getValue());

        this.setWidth(220.0);
        this.setScene(scene);
        this.show();
    }

    public void update(final Observable obs, final Object args) {
        if(obs == model) setTime(model.getValue());
    }

    private void setTime(long seconds) {
        String time = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
        label.setText(time);
    }

    @Override
    public void hide() {
        Platform.exit();
    }
}
