import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    /*To change which file you're reading, just change the arguments to
    the WheelOcfFortuneGame constructor. That way, you don't always have
    to be prompted and type in the filename and the number of puzzles every
    time you run the program.
     */
    public static void main(String [] args) throws IOException {
        WheelOfFortuneGame game = new WheelOfFortuneGame(5, "titles.txt");
    }
}
