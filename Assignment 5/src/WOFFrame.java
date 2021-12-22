import javax.swing.*;
import javax.swing.event.MenuKeyEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WOFFrame extends JFrame implements ActionListener{

    /* In this design of the game, the WOFFrame class is the class
    where you put the methods for playing the game.
     */

    /*First list your GUI objects, making them all private data fields of the class
    Note that these data fields are declared BEFORE (outside of) the constructor method.*/

    //For example
    private JMenuBar menuBar;
    private JMenu nextPuzzleMenu;
    private JLabel wofLabel;
    private JLabel puzzleLabel;
    private JLabel player1Wins;
    private JLabel player2Wins;
    private JButton spinButton;
    private JTextField guessText1;
    private int puzzleIterator = 0;
    //add more variables for GUI objects here

    /*Also, declare variables that are not GUI objects, but are places where you store
    values that are needed to play the game. For example:*/

    private StringBuffer shownPhrase = new StringBuffer();
    private String puzzle = new String();
    private String shownPhraseString = new String();
    private boolean menuButtonPressed;

    private int currentPlayerNum = 0;

    private String[] puzzles = {"gone with the wind", "avatar", "lassie come home", "wizard of oz"};
    private int[] spin = {-2, -1, 0, 100, 200, 300, 400, 500, 600};
    
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    private Player playerWhoseTurn;
    private int money = 0;
    //Add more variables here
    private JButton vowelButton;
    private JLabel p2Label;
    private JLabel p1Label;
    private JLabel moneyLabel;
    private JLabel bottomLabel;

    private int omniVal;
    private JLabel p2LabelRound;
    private JLabel p1LabelRound;
    private JMenuItem menuItem;
    private JTextField guessText2;
    private JMenu nextPuzzleMenu2;
    private JMenuItem menuItem2;

    public WOFFrame() {

        // set turn
        Random rand = new Random();
        String name1 = JOptionPane.showInputDialog("Player 1, enter your name.", null);
        player1.setName(name1);
        String name2 = JOptionPane.showInputDialog("Player 2, enter your name.", null);
        player2.setName(name2);

        menuButtonPressed = false;

        // JMenu items

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        nextPuzzleMenu = new JMenu("Puzzles");
        menuBar.add(nextPuzzleMenu);
        menuItem = new JMenuItem("Next puzzle");
        nextPuzzleMenu.add(menuItem);
        menuItem.addActionListener(this);

        nextPuzzleMenu2 = new JMenu("Credits");
        menuBar.add(nextPuzzleMenu2);
        menuItem2 = new JMenuItem("Nehemya Gugsa");
        nextPuzzleMenu2.add(menuItem2);

        int randInt = rand.nextInt(2)+1;
        if (randInt == 1) playerWhoseTurn = player1;
        if (randInt == 2) playerWhoseTurn = player2;

        puzzle = puzzles[puzzleIterator];

        shownPhrase = initializeShownPhrase();
        shownPhraseString = shownPhrase.toString();

        ///Put your GUI objects in here, laid out with a GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints positionConst = new GridBagConstraints();
        GridBagConstraints positionLabel = new GridBagConstraints();

        /*Lay out your GUI objects in your own design
        and set the column and row numbers with gridx and gridy, respectively*/

        /*Here are three examples of inserting GUI objects. You don't have to use these.*/

        // many GUI objects below as well as their position relative to one another

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 1;
        positionConst.gridy = 0;
        wofLabel = new JLabel("WHEEL OF FORTUNE");
        wofLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        add(wofLabel, positionConst);

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 0;
        positionConst.gridy = 1;
        p1Label = new JLabel(player1.getName() + "'s total: $" + player1.getMoneyForGameFinal());
        p1Label.setFont(new Font("Calibri", Font.BOLD, 20));
        add(p1Label, positionConst);

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 0;
        positionConst.gridy = 2;
        p1LabelRound = new JLabel();
        p1LabelRound.setFont(new Font("Calibri", Font.BOLD, 20));
        add(p1LabelRound, positionConst);

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 2;
        positionConst.gridy = 1;
        p2Label = new JLabel(player2.getName() + "'s total: $" + player2.getMoneyForGameFinal());
        p2Label.setFont(new Font("Calibri", Font.BOLD, 20));
        add(p2Label, positionConst);

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 2;
        positionConst.gridy = 2;
        p2LabelRound = new JLabel();
        p2LabelRound.setFont(new Font("Calibri", Font.BOLD, 20));
        add(p2LabelRound, positionConst);

        initializeRoundMoney();

        positionLabel.insets = new Insets(10, 10, 0, 10);
        positionLabel.gridx = 1;
        positionLabel.gridy = 1;
        puzzleLabel = new JLabel(shownPhraseString);
        puzzleLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        add(puzzleLabel, positionLabel);

        positionConst.gridx = 1;
        positionConst.gridy = 2;
        spinButton = new JButton("SPIN");
        add(spinButton, positionConst);
        spinButton.addActionListener(this);

        positionConst.gridx = 1;
        positionConst.gridy = 3;
        vowelButton = new JButton("BUY VOWEL");
        add(vowelButton, positionConst);
        vowelButton.addActionListener(this);

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 0;
        positionConst.gridy = 3;
        player1Wins = new JLabel(player1.getName() + " has won " + player1.getNumWins() + " games.");
        player1Wins.setFont(new Font("Calibri", Font.BOLD, 20));
        add(player1Wins, positionConst);

        positionConst.insets = new Insets(100, 100, 0, 100);
        positionConst.gridx = 2;
        positionConst.gridy = 3;
        player2Wins = new JLabel(player2.getName() + " has won " + player2.getNumWins() + " games.");
        player2Wins.setFont(new Font("Calibri", Font.BOLD, 20));
        add(player2Wins, positionConst);

        positionConst.gridx = 1;
        positionConst.gridy = 4;
        guessText1 = new JTextField("Replace this text with a consonant.");
        guessText1.setFont(new Font("Calibri", Font.PLAIN, 20));
        guessText1.setEditable(true);
        guessText1.setVisible(false);
        add(guessText1, positionConst);
        guessText1.addActionListener(this);

        positionConst.gridx = 1;
        positionConst.gridy = 4;
        guessText2 = new JTextField("Replace this text with a vowel.");
        guessText2.setFont(new Font("Calibri", Font.PLAIN, 20));
        guessText2.setEditable(true);
        guessText2.setVisible(false);
        add(guessText2, positionConst);
        guessText2.addActionListener(this);

        positionLabel.insets = new Insets(100, 100, 0, 100);
        positionLabel.gridx = 1;
        positionLabel.gridy = 5;
        moneyLabel = new JLabel("Your turn, " + playerWhoseTurn.getName() + ".");
        moneyLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        moneyLabel.setVisible(true);
        add(moneyLabel, positionLabel);

        positionLabel.insets = new Insets(100, 100, 0, 100);
        positionLabel.gridx = 1;
        positionLabel.gridy = 20;
        bottomLabel = new JLabel("__________________________________________________________");
        add(bottomLabel, positionLabel);

        //Note that the JLabel does not need an actionListener

    }

    public void initializeRoundMoney() {
        p1LabelRound.setText(player1.getName() + "'s total this round: $0");
        p2LabelRound.setText(player2.getName() + "'s total this round: $0");
        
    }

    public boolean isConsonant(char c) {
        // checks if character is consonant
        return (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u');
    }

    /*Because you say that this class implements the interface ActionListener, you have to
    create an ActionPerformed method. This is where you can identify which object generated
    an event, like the clicking of a button or entering text into a JTextField*/
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        if (e.getSource() == spinButton) {
            money = doSpin();
            // lose turn, bankrupt, special feature, etc.
            if (money == 0) {
                moneyLabel.setText("Sorry, " + playerWhoseTurn.getName() + ", you lose your turn.");
                swapTurns(playerWhoseTurn.getPlayerNum());
            }
            else if (money == -1) {
                bankruptcy(playerWhoseTurn.getPlayerNum());
                swapTurns(playerWhoseTurn.getPlayerNum());
            }

            else if (money == -2) {
                specialFeature();
            }
            else {
                moneyLabel.setText(playerWhoseTurn.getName() + ", you stand to win $" + money);
                moneyLabel.setVisible(true);
                guessText1.setVisible(true);
                guessText2.setVisible(false);
            }
        // action in textFields
        } else if (e.getSource() == guessText1) {
            guessAConsonant(money, true, guessText1.getText());
        } else if (e.getSource() == vowelButton) {
            moneyLabel.setVisible(true);
            if (playerWhoseTurn.getMoneyForGameTemp() >= 250) {
                moneyLabel.setText(playerWhoseTurn.getName() + ", you lose $250");
                guessText1.setVisible(false);
                guessText2.setVisible(true);
            } else if (playerWhoseTurn.getMoneyForGameTemp() < 250) {
                moneyLabel.setText("Sorry, " + playerWhoseTurn.getName() + ", you can't afford that. Your turn is over.");
                swapTurns(playerWhoseTurn.getPlayerNum());
            }
        }  else if (e.getSource() == guessText2) {
            guessAVowel(-250, guessText2.getText());
        // action in JMenu
        } else if (e.getSource() == menuItem) {
            menuButtonPressed = true;
            playerWins(menuButtonPressed);
        } 
        /*Put in cases for all GUI objects that can generate an "event". It's generally
        best to use method calls to handle each event.*/

    }

    public void specialFeature() {
        // JOptionPane special feature (double or nothing)
        String dorN = JOptionPane.showInputDialog("Double or nothing? \nIf you guess a correct consonant, you double your round total. \nIf you guess incorrectly, you lose it all.\nEnter your consonant guess to participate.\nClick 'Cancel' to decline.", null);
        boolean b = false;
        guessAConsonant(playerWhoseTurn.getMoneyForGameTemp(), b, dorN);

    }

    public int guessAVowel(int money, String charString) {

        boolean turnOver = false;
        boolean puzzleSolved = false;

        /*If the player guesses a vowel instead of a consonant, or the player guesses a
        consonant that was already guessed or the player guesses a consonant that
        is not in the puzzle, 1 is returned to takeTurn(), meaning the player's turn is over.
        Otherwise, the player gets numInstances * spinValue dollars added
        to their winnings so far for the game, and 2 is returned to takeTurn(), meaning
        that the player's turn continues.*/
        
        char c = charString.charAt(0);

        int count = 0;
        int countR = 0;

        guessText2.setText("Enter the full phrase here to solve.");

        boolean consonant = isConsonant(c);

        playerWhoseTurn.setMoneyForGameTemp(money);

        if (playerWhoseTurn.getPlayerNum() == 1) {
            p1LabelRound.setText(player1.getName() + "'s total this round: $"  + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
        }
        if (playerWhoseTurn.getPlayerNum() == 2) {
            p2LabelRound.setText(player2.getName() + "'s total this round: $" + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
        }

        if (charString.length() > 1) {
            solveThePuzzle(charString);
        }

        else if (consonant == true) {
            swapTurns(currentPlayerNum);
            moneyLabel.setText("That is not a vowel. Your turn, " + playerWhoseTurn.getName());
            turnOver = true;
        }

        else if (consonant == false && charString.length() <= 1) {
            for (int i = 0; i < shownPhrase.length(); ++i) {
                if (c == puzzle.charAt(i)) {
                    count++;
                    if (c != shownPhrase.charAt(i)) {
                        shownPhrase.replace(i, i+1, charString);
                        shownPhraseString = shownPhrase.toString();
                        puzzleLabel.setText(shownPhraseString);
                        moneyLabel.setText("Still your turn, " + playerWhoseTurn.getName() + ". Spin, buy a vowel, or solve.");
                    }
                else if (c == shownPhrase.charAt(i)) {
                    while (countR == 0) {
                        swapTurns(playerWhoseTurn.getPlayerNum());
                        moneyLabel.setText("That letter has already been guessed. Your turn, " + playerWhoseTurn.getName());
                        countR++;
                    }
                }
            }
        }

        if (count == 0) { 
                turnOver = true;
                swapTurns(playerWhoseTurn.getPlayerNum());
                moneyLabel.setText("Sorry, not quite right. Your turn, " + playerWhoseTurn.getName());
            }
        }
            
        String shownPhraseString = shownPhrase.toString();

        if (puzzle.equals(shownPhraseString)) {
            puzzleSolved = true;
        }

        currentPlayerNum = playerWhoseTurn.getPlayerNum();

        if (turnOver == false && puzzleSolved == false) return 2;

        if (puzzleSolved) {
            playerWins(false);
            setTotalMoney(currentPlayerNum);
        }
        return -1; //this is just a placeholder
    }

    public void setRoundMoney(int currentPlayerNum) {
        // sets round money 
        if (currentPlayerNum == 1) {
            p1LabelRound.setText(player1.getName() + "'s total this round: $"  + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
        }
        if (currentPlayerNum == 2) {
            p2LabelRound.setText(player2.getName() + "'s total this round: $"  + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
        }

    }

    public void bankruptcy(int currentPlayerNum) {  
        // in case of bankruptcy
        if (currentPlayerNum == 1) {
            int tempVal = player1.getMoneyForGameTemp() * -1;
            player1.setMoneyForGameTemp(tempVal);
            moneyLabel.setText("You've gone bankrupt. It is now " + player2.getName() + "'s turn.");
            setRoundMoney(currentPlayerNum);
        }
        if (currentPlayerNum == 2) {
            int tempVal = player2.getMoneyForGameTemp() * -1;
            player2.setMoneyForGameTemp(tempVal);
            moneyLabel.setText("You've gone bankrupt. It is now " + player1.getName() + "'s turn.");
            setRoundMoney(currentPlayerNum);
        }

    }

    public void setTotalMoney(int currentPlayerNum) {
        // sets total money for both players
        if (currentPlayerNum == 1) {
            p1Label.setText(player1.getName() + "'s total: $"  + String.valueOf(player1.getMoneyForGameFinal()));
        }
        if (currentPlayerNum == 2) {
            p2Label.setText(player2.getName() + "'s total: $"  + String.valueOf(player2.getMoneyForGameFinal()));
        }
    }

    public void setTotalWins() {
        // sets total wins for both players
        player1Wins.setText(player1.getName() + " has won " + player1.getNumWins() + " games.");
        player2Wins.setText(player2.getName() + " has won " + player2.getNumWins() + " games.");
    }

    public int guessAConsonant(int money, boolean b, String charString) {
        // boolean b checks if method was accessed traditionally or by special feature (thus affecting how much money can be earned)

        boolean turnOver = false;
        boolean puzzleSolved = false;

        /*If the player guesses a vowel instead of a consonant, or the player guesses a
        consonant that was already guessed or the player guesses a consonant that
        is not in the puzzle, 1 is returned to takeTurn(), meaning the player's turn is over.
        Otherwise, the player gets numInstances * spinValue dollars added
        to their winnings so far for the game, and 2 is returned to takeTurn(), meaning
        that the player's turn continues.*/
        
        char c = charString.charAt(0);

        int count = 0;
        int countR = 0;
        int countR2 = 0;

        guessText1.setText("Enter the full phrase here to solve.");

        boolean consonant = isConsonant(c);

        if (charString.length() > 1) {
            solveThePuzzle(charString);
        }

        else if (consonant == false) {
            swapTurns(playerWhoseTurn.getPlayerNum());
            moneyLabel.setText("That is not a consonant. Your turn, " + playerWhoseTurn.getName());
            turnOver = true;
        }

        else if (consonant == true && charString.length() <= 1) {
            for (int i = 0; i < shownPhrase.length(); ++i) {
                if (c == puzzle.charAt(i)) {
                    count++;
                    if (c != shownPhrase.charAt(i)) {
                        shownPhrase.replace(i, i+1, charString);
                        shownPhraseString = shownPhrase.toString();
                        puzzleLabel.setText(shownPhraseString);
                        moneyLabel.setText("Still your turn, " + playerWhoseTurn.getName() + ". Spin, buy a vowel, or solve.");
                        if (b == true) playerWhoseTurn.setMoneyForGameTemp(money);
                        if (b == false) {
                            while (countR2 == 0) {
                                playerWhoseTurn.setMoneyForGameTemp(money);
                                countR2++;
                            }
                        }
                        if (playerWhoseTurn.getPlayerNum() == 1) {
                            p1LabelRound.setText(player1.getName() + "'s total this round: $"  + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
                        }
                        if (playerWhoseTurn.getPlayerNum() == 2) {
                            p2LabelRound.setText(player2.getName() + "'s total this round: $" + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
                        }
                    }
                else if (c == shownPhrase.charAt(i)) {
                    while (countR == 0) {
                        swapTurns(playerWhoseTurn.getPlayerNum());
                        moneyLabel.setText("That letter has already been guessed. Your turn, " + playerWhoseTurn.getName());
                        countR++;
                    }
                }
            }
        } if (count == 0) { 
                turnOver = true;
                if (b == false) {
                    int tempVal = playerWhoseTurn.getMoneyForGameTemp() * -1;
                    playerWhoseTurn.setMoneyForGameTemp(tempVal);
                    if (playerWhoseTurn.getPlayerNum() == 1) {
                        p1LabelRound.setText(player1.getName() + "'s total this round: $"  + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
                    }
                    if (playerWhoseTurn.getPlayerNum() == 2) {
                        p2LabelRound.setText(player2.getName() + "'s total this round: $" + String.valueOf(playerWhoseTurn.getMoneyForGameTemp()));
                    }
                } swapTurns(playerWhoseTurn.getPlayerNum());
                moneyLabel.setText("Sorry, not quite right. Your turn, " + playerWhoseTurn.getName());
            }
        }

        String shownPhraseString = shownPhrase.toString();

        if (puzzle.equals(shownPhraseString)) {
            puzzleSolved = true;
        }

        currentPlayerNum = playerWhoseTurn.getPlayerNum();

        if (turnOver == false && puzzleSolved == false) return 2;

        if (puzzleSolved) {
            playerWins(false);
            setTotalMoney(currentPlayerNum);
        }
        return -1; //this is just a placeholder
    }

    public void swapTurns(int currentPlayerNum) {
        if (currentPlayerNum == 1) {
            player1 = playerWhoseTurn;
            playerWhoseTurn = player2;
        } if (currentPlayerNum == 2) {
            player2 = playerWhoseTurn;
            playerWhoseTurn = player1;
        } 
    }

    public int solveThePuzzle(String charString) {

        boolean turnOver = false;
        boolean puzzleSolved = false;

        /*If the player guesses the puzzle correctly, the player gets all the money
        they accumulated so far in this game, and the others win nothing for this game.
        Then a 0 is returned to takeTurn(), meaning that the puzzle has been
        solved; otherwise 1 is returned, meaning that the player's turn is over.*/

        if (!charString.equals(puzzle)) { 
            swapTurns(playerWhoseTurn.getPlayerNum());
            moneyLabel.setText("Sorry, not quite right. Your turn, " + playerWhoseTurn.getName());
        }
        
        else {
            puzzleSolved = true;
            playerWins(false);
            setTotalMoney(currentPlayerNum);
        }

        if (turnOver == false && puzzleSolved == false) return 2;
        if (turnOver = true && puzzleSolved == false) return 1;
        if (puzzleSolved = true) return 0;

        return -1; //this is just a placeholder

    }

    public void playerWins(boolean menuButtonPressed) {
        
        // method for moving on to next puzzle (accessible from menu or by solving current puzzle)

        moneyLabel.setText(playerWhoseTurn.getName() + ", it is your turn.");
        if (puzzleIterator < puzzles.length - 1) {
            puzzleIterator++;
            puzzle = puzzles[puzzleIterator];

            shownPhrase = initializeShownPhrase();
            shownPhraseString = shownPhrase.toString();

            puzzleLabel.setText(shownPhraseString);

            int cash = playerWhoseTurn.getMoneyForGameTemp();
            playerWhoseTurn.setMoneyForGameFinal(cash);

            int tempVal = player1.getMoneyForGameTemp() * -1;
            player1.setMoneyForGameTemp(tempVal);
            tempVal = player2.getMoneyForGameTemp() * -1;
            player2.setMoneyForGameTemp(tempVal);
            // if accessed from menu
            if (menuButtonPressed == false) {
                playerWhoseTurn.setNumWins(1);
                setTotalWins();
            }
            menuButtonPressed = false;
            initializeRoundMoney();
        } else {
            String input = JOptionPane.showInputDialog("Game Over. Enter 'Yes' to play again.", null);
            if (input.equals("Yes") || input.equals("yes")) {
                dispose();
                puzzleIterator = 0;
                puzzle = puzzles[puzzleIterator];
                Main.main(null);
                // set redo here
            }
        }
        
    }

    public StringBuffer initializeShownPhrase() {
        //Initialize shown phase with underscores and blanks
        /*Tip: You might want to use a StringBuffer rather than a String
        for the hiddenPhrase so that you can use the replace method to
        replace a character = ' ' or '_'
         */
        String temp = "";
        for (int i = 0; i < puzzle.length(); ++i) {
            if (puzzle.charAt(i) != ' ') {
                temp = temp + "-";
            }
            if (puzzle.charAt(i) == ' ') {
                temp = temp + " ";
            }
        }
        shownPhrase = shownPhrase.replace(0, shownPhrase.length(), temp);
        return(shownPhrase);
    }

    public int doSpin() {
        Random rand = new Random();
        int randInt = rand.nextInt(8);
        int spinVal = spin[randInt];

        return spinVal;
    }
}