// The game is straightforward enough.
// Click 'Spin' to spin the wheel and 'Buy vowel' to buy a vowel.
// You can enter the entire puzzle in the text field to solve it at any given time.
// We choose a player to guess first randomly.
// There is a special 'double or nothing' feature. The details are more clearly laid out when it comes up.
// CSC 112
// 12.01.2021
// Nehemya Gugsa

import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        WOFFrame wofFrame = new WOFFrame();
        wofFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wofFrame.pack();
        wofFrame.setVisible(true);
    }
}
