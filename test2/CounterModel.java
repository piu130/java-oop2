package test.test2;

import java.io.Serializable;
import java.util.Observable;

public class CounterModel extends Observable implements Serializable {
    private long value;

    /**
     * Increment value
     */
    public void inc() {
        value++;
        setChanged();
        notifyObservers();
    }

    /**
     * Reset value
     */
    public void reset() {
        value = 0;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns value
     * @return Value
     */
    public long getValue() {
        return value;
    }
}
