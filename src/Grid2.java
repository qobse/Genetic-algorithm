/**
 * Grid2.java
 * 
 * This class represents a single cell in the Skyscraper puzzle grid.
 * 
 * Author: Yaqoob Yaghoubi
 * Date: 
 * 
 * Description:
 * This class contains methods to get and set the value of a cell in the puzzle grid.
 */

public class Grid2 {
    private int value;

    public Grid2(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
