package ru.maklas.libs;

/**
 * Counts from minimum value to maximum (last will be maximum - 1)
 * Created by maklas on 06-Nov-17.
 */

public class Counter {

    private final int min;
    private final int max;
    private int value;

    public Counter(int min, int max) {
        this.min = min;
        this.max = max;
        this.value = min;
    }

    /**
     * Take the next number and increase internal counter
     * @return
     */
    public int next(){
        final int valueToReturn = value++;
        if (value >= max){
            value = min;
        }
        return valueToReturn;
    }

    /**
     * Get the next value to be fetched, but not increase internal counter
     */
    public int peak(){
        return value;
    }

    /**
     * Sets to minimum
     */
    public void reset(){
        this.value = min;
    }

}
