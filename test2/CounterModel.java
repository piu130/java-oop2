package test.test2;

import java.io.Serializable;
import java.util.Observable;

public class CounterModel extends Observable implements Serializable {
    private long value;

    public void inc() {
        value++;
        setChanged();
        notifyObservers();
    }

    public void reset() {
        value = 0;
        setChanged();
        notifyObservers();
    }

    public long getValue() {
        return value;
    }
}
