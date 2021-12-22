import java.util.ArrayList;
import java.util.Random;

public class Wheel {
    /*Spinning -1 means "Bankrupt. Spinning -2 means "Lose a Turn"*/

    private int[] moneyValues = {100, 200, 300, 400, 500, 600, 700, 900, 900, 10000, -1, -2};

    Random rand = new Random();

    public int spinWheel() {
        //Put code here
        int spin = rand.nextInt(12);
        return moneyValues[spin];
    }
}
