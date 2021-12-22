import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class WheelOfFortuneGame {
    // private data fields
    private Player player1;
    private Player player2;
    private Player whoseTurn;
    private Scanner scnr;
    private String[] hiddenPhrases;
    private StringBuffer shownPhrase;
    private String hiddenPhrase;
    private int numPhrases;
    private Wheel wheel;
    private int phraseIterator;
    private int player1Neg;
    private int player2Neg;

    // random and scanner initialize

    Random rand = new Random();
    Scanner inputFile = null;
    

    public WheelOfFortuneGame(int n, String fileName) throws IOException {

        // initializing

        scnr = new Scanner(System.in);

        player1 = new Player(1);
        player2 = new Player(2);

        hiddenPhrases = new String[n];
        shownPhrase = new StringBuffer();
        hiddenPhrase = "";
        numPhrases = n;

        phraseIterator = 0;
        

        FileInputStream fileByteStream = new FileInputStream(fileName);

        // taking player names

        System.out.print("Player 1, what is your name? ");
        String player1Name = scnr.next();
        player1.setName(player1Name);

        System.out.print("Player 2, what is your name? "); 
        String player2Name = scnr.next();
        player2.setName(player2Name);       

        inputFile = new Scanner(fileByteStream);

        //Read in the text files of puzzles

        int it = 0;
        while (inputFile.hasNext()) {
            hiddenPhrases[it] = inputFile.nextLine();
            it++;
        }

        /*The constructor method reads in the puzzles and initializes data fields. It
        calls playMatch(). A match is multiple games. */

        //initialize the data fields of WheelOfFortuneGame as necessary


        //randomly determine who goes first

        int randInt = rand.nextInt(2) + 1;

        if (randInt == 1) {                 // player 1 goes first
            whoseTurn = player1;
        }

        if (randInt == 2) {                 // player 2 goes first
            whoseTurn = player2;
        }

        playMatch();
    }

    public void playMatch() {
        //Keep playing games as long as there are more puzzles to do and the players want to continue
        System.out.print("How many rounds would you like to play out of 5 total? (Type the digit.) ");
        int numRounds = scnr.nextInt();
        int roundCount = 0;

        // round iterator
        
        for (int i = 0 ; i < numRounds; ++i) {
            roundCount++;
            System.out.print("Do you want to start Round " + roundCount + "? (Y/N) ");
            String answer = scnr.next();
            if ((answer.equals("Y")) || (answer.equals("y")) || (answer.equals("Yes")) || (answer.equals("yes"))) {
                hiddenPhrase = hiddenPhrases[phraseIterator];
                initializeShownPhrase();
                player1.setMoneyForGameTemp(player1Neg);
                player2.setMoneyForGameTemp(player2Neg);
                play();
            }
            if ((answer.equals("N")) || (answer.equals("n")) || (answer.equals("No")) || (answer.equals("no"))) {
                endScreen();
                System.exit(0);
            }
            phraseIterator++;
        }

        // end screen if played complete number of rounds

        if (phraseIterator == numRounds) {
            endScreen();
        }
    }

    public void endScreen() {
        // end screen
        System.out.println("");
        System.out.println("Game over.");
        System.out.println("" + player1.getName() + ", you have won a total $" + player1.getMoneyForGameFinal() + ".");
        System.out.println("Furthermore, you have won " + player1.getNumWins() + " round(s).");
        System.out.println("" + player2.getName() + ", you have won a total $" + player2.getMoneyForGameFinal() + ".");
        System.out.println("Furthermore, you have won " + player2.getNumWins() + " round(s).");
    }

    private boolean isConsonant(char c) {
        // checks if character is consonant
        return (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u');
    }

    public void play() {
        /*This method is the overview of playing one game. It contains a loop
        for switching between one player's turn and another until the puzzle is
        solved and the game is over. Then execution returns to playMatch after one
        game is played, where the players can say they want to play another match,
        which can be done as long as there are puzzles left.
         */
        
        int returnVal = 2;
        
        while (returnVal == 2) {
            returnVal = takeTurn();

            if (returnVal == 1) {
                // switch turns
                System.out.println("Your turn is over.");
                System.out.println("");
                switchTurns(returnVal);
                returnVal = 2; 
                }     
            }

            if (returnVal == 0) {
                System.out.println(player1.getName() + ", your earnings thus far total $" + player1.getMoneyForGameFinal() + ". ");
                System.out.println("You have won " + player1.getNumWins() + " round(s). ");
                System.out.println(player2.getName() + ", your earnings thus far total $" + player2.getMoneyForGameFinal() + ". ");
                System.out.println("You have won " + player2.getNumWins() + " round(s). ");
                System.out.println("");
            }

        //Initialize whatever needs to be reset at the beginning of each game

        //Make a shownPhrase with the same number of characters as the hiddenPhrase

        //Let player 1 and player 2 alternate taking turns until someone solves the puzzle

    }

    public void switchTurns(int returnVal) {
        // switches player whose turn if player does not guess any new correct letters
        int num = 0;
        num = whoseTurn.getPlayerNum();
        if (num == 1) {
            player1 = whoseTurn;
            whoseTurn = player2;
        }
        else if (num == 2) {
            player2 = whoseTurn;
            whoseTurn = player1;
        }
    }

    public int takeTurn() {
        /*takeTurn represents the turn of one player. It has a loop in it because
        a player can keep guessing letters until they make a mistake, spin a "Bankrupt"
        on the wheel, or spin a "Lose a Turn".
        Tip: Wherever you are in a loop, you can drop out the loop entirely with "return"
        in the cases where this makes sense.
         */

        /*One way to make this work:
        Return 2 from a case below means that the player's turn is not over
        Return 1 means that the player's turn is over
        Return 0 means that the puzzle is solved*/

        /*loop for a player who keeps guessing letters correctly
        {
            spin the wheel

            consider "bankrupt" or "lose a turn" cases

            otherwise allow the player to choose from the three options
                case 1: solveThePuzzle
                case 2: guessAVowel
                case 3: guessAConsonant

                return 0, 1, or 2 to play() as described abovce
         */

        String currPlayerName = "";
        currPlayerName = whoseTurn.getName();

        // randomly chooses wheel value on player input

        System.out.print("It is " + currPlayerName + "'s turn. Enter 'Spin' to spin the wheel. ");
        String enter = scnr.next();

        int dollars = 0;

        // dollars = wheel value

        wheel = new Wheel();
        dollars = wheel.spinWheel();

        // players option if money available

        while (dollars >= 0) {
            System.out.println("You stand to win $" + dollars);
            System.out.println("");

            System.out.println("What would you like to do?");
            System.out.print("Enter 1 to solve the puzzle, 2 to buy a vowel (-$250), and 3 to guess a consonant. ");
            switch (scnr.nextInt()) {
                case 1: 
                    return solveThePuzzle();
                case 2:
                    return guessAVowel(dollars);
                case 3:
                    return guessAConsonant(dollars);
            }
        }

        // bankruptcy and losing a turn
        
        if (dollars == -1) {
            System.out.println("You are bankrupt.");
            int tempVal = whoseTurn.getMoneyForGameTemp() * -1;
            whoseTurn.setMoneyForGameTemp(tempVal);
            return 1;
        }

        if (dollars == -2) {
            System.out.println("You lose a turn.");
            return 1;
        }

        else return -1;
    }

    /////////////////////
    public int solveThePuzzle() {

        scnr.nextLine();

        boolean turnOver = false;
        boolean puzzleSolved = false;

        /*If the player guesses the puzzle correctly, the player gets all the money
        they accumulated so far in this game, and the others win nothing for this game.
        Then a 0 is returned to takeTurn(), meaning that the puzzle has been
        solved; otherwise 1 is returned, meaning that the player's turn is over.*/

        String charString = new String();

        System.out.println(shownPhrase);
        System.out.println("Enter the phrase:");
        charString = scnr.nextLine();

        if (!charString.equals(hiddenPhrase)) { 
            System.out.println("");
            System.out.println("You have guessed incorrectly.");
            return 1;
        }
        
        else {
            puzzleSolved = true;
            
            printWin();
        }

        if (turnOver == false && puzzleSolved == false) return 2;
        if (turnOver = true && puzzleSolved == false) return 1;
        if (puzzleSolved = true) return 0;

        return -1; //this is just a placeholder

    }

    public void printWin() {
        
        // prints when puzzle is solved

        System.out.println("");
        System.out.println("Congratulations, " + whoseTurn.getName() + "! You have solved the puzzle and won this round.");
        System.out.println("Your winnings from this round are added to your total earnings.");
        whoseTurn.setNumWins(1);
        int cash = whoseTurn.getMoneyForGameTemp();
        whoseTurn.setMoneyForGameFinal(cash);

        int pNum = whoseTurn.getPlayerNum();
        if (pNum == 1) {
            System.out.println("Unfortunately, " + player2.getName() + ", you do not get to keep your earnings from this round.");
        }
        else if (pNum == 2) {
            System.out.println("Unfortunately, " + player1.getName() + ", you do not get to keep your earnings from this round.");
        }

        int tempVal = player1.getMoneyForGameTemp() * -1;
        player1.setMoneyForGameTemp(tempVal);
        tempVal = player2.getMoneyForGameTemp() * -1;
        player2.setMoneyForGameTemp(tempVal);
    

        System.out.println("");
        
    }

    public int guessAVowel(int money) {

        boolean turnOver = false;
        boolean puzzleSolved = false;

        /*If the player doesn't have $250 (in the current game's
        winnings to pay for the vowel, or the player guesses a
        consonant instead of a vowel, or the player guesses a vowel that was
        already guessed, or the player guesses a vowel that's not in the
        puzzle, 1 is returned, meaning the player's turn is over.
        Otherwise the player pays $250 out of this games winnings so far,
        and 2 is returned, meaning that the player's turn continues.
        (No money is awarded for the vowels present in the word.)*/
        
        if (whoseTurn.getMoneyForGameTemp() >= 250) {
            whoseTurn.setMoneyForGameTemp(-250);
        } else {
            System.out.println("Sorry, you can't currently afford to buy a vowel this round.");
            System.out.println("");
            return 1;
        }
        
        System.out.print("Guess a vowel (lowercase). ");
        String charString = scnr.next();
        char c = charString.charAt(0);

        boolean consonant = isConsonant(c);
        if (consonant == true) {
            System.out.println(c + " is not a vowel.");
            return 1;
        }
        
        int count = 0;
        int countR = 0;

        for (int i = 0; i < hiddenPhrase.length(); ++i) {
            if (c == hiddenPhrase.charAt(i)) {
                if (c != shownPhrase.charAt(i)) {
                    shownPhrase.replace(i, i+1, charString);
                    count++;
                } else if (c == shownPhrase.charAt(i)) {
                    while (countR == 0) {
                    System.out.println("That letter has already been guessed.");
                    countR++;
                    }
                }
            }
        } if (count == 0) { 
            turnOver = true;
        }

        String shownPhraseString = shownPhrase.toString();

        if (hiddenPhrase.equals(shownPhraseString)) return 0;

        System.out.println("");
        System.out.println("You have guessed " + count + " correct letters.");
        System.out.println(shownPhrase);
        System.out.println("You have earned $" + whoseTurn.getMoneyForGameTemp() + " so far this round. ");
        System.out.println("");

        if (turnOver == false && puzzleSolved == false) return 2;
        if (turnOver = true && puzzleSolved == false) return 1;
        if (puzzleSolved = true) return 0;

        return -1; //this is just a placeholder
    }

    public int guessAConsonant(int money) {

        boolean turnOver = false;
        boolean puzzleSolved = false;

        /*If the player guesses a vowel instead of a consonant, or the player guesses a
        consonant that was already guessed or the player guesses a consonant that
        is not in the puzzle, 1 is returned to takeTurn(), meaning the player's turn is over.
        Otherwise, the player gets numInstances * spinValue dollars added
        to their winnings so far for the game, and 2 is returned to takeTurn(), meaning
        that the player's turn continues.*/
        
        System.out.print("Guess a consonant (lowercase). ");
        String charString = scnr.next();
        char c = charString.charAt(0);

        boolean consonant = isConsonant(c);
        if (consonant == false) {
            System.out.println(c + " is not a consonant.");
            return 1;
        }
        
        int count = 0;
        int countR = 0;
        for (int i = 0; i < hiddenPhrase.length(); ++i) {
            if (c == hiddenPhrase.charAt(i)) {
                if (c != shownPhrase.charAt(i)) {
                    shownPhrase.replace(i, i+1, charString);
                    count++;
                    whoseTurn.setMoneyForGameTemp(money);
                } 
                else if (c == shownPhrase.charAt(i)) {
                    while (countR == 0) {
                        System.out.println("That letter has already been guessed.");
                        countR++;
                    }
                }
            }
        } if (count == 0) { 
            turnOver = true;
        }

        System.out.println("");
        System.out.println("You have guessed " + count + " correct letters.");
        System.out.println(shownPhrase);
        System.out.println("You have earned $" + whoseTurn.getMoneyForGameTemp() + " so far this round. ");
        System.out.println("");

        String shownPhraseString = shownPhrase.toString();

        if (hiddenPhrase.equals(shownPhraseString)) return 0;

        if (turnOver == false && puzzleSolved == false) return 2;
        if (turnOver = true && puzzleSolved == false) return 1;
        if (puzzleSolved = true) return 0;

        return -1; //this is just a placeholder
    }

    public void initializeShownPhrase() {
        //Initialize shown phase with underscores and blanks
        /*Tip: You might want to use a StringBuffer rather than a String
        for the hiddenPhrase so that you can use the replace method to
        replace a character = ' ' or '_'
         */
        String temp = "";
        for (int i = 0; i < hiddenPhrase.length(); ++i) {
            if (hiddenPhrase.charAt(i) != ' ') {
                temp = temp + "-";
            }
            if (hiddenPhrase.charAt(i) == ' ') {
                temp = temp + " ";
            }
        }
        shownPhrase = shownPhrase.replace(0, shownPhrase.length(), temp);
        System.out.println(shownPhrase);

    }
}
