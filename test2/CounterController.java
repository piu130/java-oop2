package test.test2;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CounterController implements Serializable {

    private transient AnimationTimer timer;
    private transient BooleanProperty running;
    private CounterModel model;

    public CounterController(CounterModel model) {
        this.model = model;
        running = new SimpleBooleanProperty(false);
        initTimer();
    }

    private void initTimer() {
        timer = new AnimationTimer() {
            private long currentTime;

            public void handle(long now) {
                if (now > currentTime + 1_000_000_000) {
                    currentTime = now;
                    model.inc();
                }
            }
        };
    }

    public void start() {
        running.setValue(true);
        timer.start();
    }

    public void stop() {
        running.setValue(false);
        timer.stop();
    }

    public void reset() {
        model.reset();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // write BooleanProperty (not serializable)
        oos.writeBoolean(isRunning());
    }

    private void readObject(ObjectInputStream ois) throws IOException {
        try {
            ois.defaultReadObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // read BooleanProperty
        running = new SimpleBooleanProperty(ois.readBoolean());
        // init timer (not serializable)
        initTimer();
        if(running.getValue()) start();
    }

    public CounterModel getModel() {
        return model;
    }

    public boolean isRunning() {
        return running.get();
    }

    public BooleanProperty runningProperty() {
        return running;
    }
}
