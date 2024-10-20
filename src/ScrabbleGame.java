import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScrabbleGame {

    List<Player> players;
    Bag bag;
    Board board;
    Parser parser;
    Scanner sc;
    int turn = 0;

    ArrayList<ScrabbleView> views;

    /**
     * Create the game.
     */
    public ScrabbleGame() {
        players = new ArrayList<>();
        bag = new Bag();
        board = new Board();
        parser = new Parser();
        sc = new Scanner(System.in);

        views = new ArrayList<>();

        parser.loadWordsFromFile("src/Dictionary.txt");
    }

    /**
     * Add views for MVC
     */
    public void addView(ScrabbleView view){
        views.add(view);
    }

    /**
     * Updates the views for all the subscribed viewers
     */

    public void updateViews(){
        for (ScrabbleView v : views){
            v.update(this);
        }
    }


    /**
     * Add players to the game.
     */
    public void participants() {
        boolean numPlayers = false;

        while (!numPlayers) {
            System.out.println("Provide the number of players participating in the game (2-4 players): ");
            int n = sc.nextInt();

            if (n < 2) {
                System.out.println("Not enough players!");
            } else if (n > 4) {
                System.out.println("Too many players!");
            } else {
                for (int i = 0; i < n; i++) {
                    System.out.println("Player "+(i+1)+": ");
                    String playerName = sc.next();
                    Player player = new Player(playerName);
                    player.getHand().setLetters(bag.getAlphabet());
                    players.add(player);
                }
                numPlayers = true;
            }
        }
    }



    /**
     * Adds players to the game (Compatible for Milestone 2)
     */

    public void MVCparticipants(int n) {
        boolean numPlayers = false;

        while (!numPlayers) {

            if (n < 2) {
                JOptionPane.showMessageDialog(null,"Not enough players!");
            } else if (n > 4) {
                JOptionPane.showMessageDialog(null,"Too many players!");
            } else {
                for (int i = 0; i < n; i++) {
                    int j = i+1;
                    String playerName = JOptionPane.showInputDialog("Enter player "+ j +" Name: ");
                    Player player = new Player(playerName);
                    player.getHand().setLetters(bag.getAlphabet());
                    players.add(player);
                }
                numPlayers = true;
            }
        }
    }




    /**
     * Set a letter on the board for a player.
     */
    public void setLetterOnBoard(int y, int x, char letter, Player player) {
        Hand playerHand = player.getHand();
        if (playerHand.getLetters().contains(letter)) {
            System.out.println("Letter Placed is: "+letter+"\n");
            board.setLetterOnBoard(y,x,letter);
            playerHand.getLetters().remove(playerHand.getLetterPosition(letter));
            playerHand.refillHand();
        } else {
            System.out.println("The letter "+letter+" is not in your hand!\n");
        }
    }

    /**
     * Gets the turn of the current player
     */
    public Player getCurrentPlayer(){
        return players.get(turn%players.size());
    }


    /**
     *Plays the turn of the player, set's the letter, on X and Y axis.
     * Makes sure that the words should be branched
     * Makes sure that the inputted word is a valid word
     */
    public void playTurn(Player currentPlayer) {
        List<int[]> placedPositions = new ArrayList<>();
        boolean turnOver = false;

        while (!turnOver) {
            turn++;
            System.out.print("Enter the letter to place: ");
            char letter = sc.next().toUpperCase().charAt(0);
            System.out.print("Enter the X coordinate: ");
            int x = sc.nextInt();
            System.out.print("Enter the Y coordinate: ");
            int y = sc.nextInt();

            if (turn == 1) {
                if (x != 7 && y != 7) {
                    int a = 0;
                    int b = 0;
                    while (a != 7 && b != 7) {
                        System.out.println();
                        System.out.println("Illegal move, please start from the board center: (X:7, Y:7)");
                        System.out.print("Enter the X coordinate: ");
                        a = sc.nextInt();
                        System.out.print("Enter the Y coordinate: ");
                        b = sc.nextInt();
                    }
                    x = a;
                    y = b;
                }
                setLetterOnBoard(y, x, letter, currentPlayer);
                placedPositions.add(new int[]{x,y});
            } else {
                if ((x + 1 < 15 && board.getLetterOnBoard(y, x + 1) != ' ') ||
                        (y + 1 < 15 && board.getLetterOnBoard(y + 1, x) != ' ') ||
                        (x - 1 >= 0 && board.getLetterOnBoard(y, x - 1) != ' ') ||
                        (y - 1 >= 0 && board.getLetterOnBoard(y - 1, x) != ' ') ||
                        turn == 1) {

                    setLetterOnBoard(y, x, letter, currentPlayer);
                    placedPositions.add(new int[]{x, y});
                } else {
                    board.printBoard();
                    System.out.println("Your letter must connect to other letters on the board.");
                    continue;
                }
            }

            System.out.print("Do you want to place another letter? (yes/no): ");
            String placeAnother = sc.next();

            if (placeAnother.equalsIgnoreCase("no")) {
                String formedWord = checkValidWord(y,x);
                if (parser.isValidWord(formedWord)) {
                    System.out.println(formedWord +" is a valid word!");
                    currentPlayer.calculateWordScore(formedWord);
                    turnOver = true;
                } else {
                    System.out.println(formedWord+ " is not a valid English word!");

                    for (int[] pos : placedPositions) {

                        board.setDeleteLetterFromBoard(pos[1],pos[0],' ');
                    }
                    placedPositions.clear();
                    getPlayerHand(currentPlayer);
                    board.printBoard();
                }
            }
        }
    }

    /**
     *Checks if the word placed is a valid word
     */
    public String checkValidWord(int y, int x) {

        StringBuilder word = new StringBuilder();

        for(int i =x ;i >=0 &&board.getLetterOnBoard(y,i) != ' ';i--) {
            word.insert(0,board.getLetterOnBoard(y, i));
        }
        for(int i= x+1;i<15 && board.getLetterOnBoard(y,i)!= ' ';i++) {
            word.append(board.getLetterOnBoard(y,i));
        }
        for(int i= y-1;i>=0&& board.getLetterOnBoard(i,x)!= ' '; i--) {
            word.insert(0,board.getLetterOnBoard(i,x));
        }
        for(int i= y+1; i<15 &&board.getLetterOnBoard(i,x)!= ' '; i++) {
            word.append(board.getLetterOnBoard(i,x));
        }

        return word.toString();
    }

    /**
     * This method defines the commands in the game
     */
    public void play() {
        printIntro();
        participants();

        boolean gameOn = true;
        while (gameOn) {
            for (Player currentPlayer : players) {
                board.printBoard();
                System.out.println("It's "+currentPlayer.getName()+"'s turn.");
                getPlayerHand(currentPlayer);

                printActions();
                Command command = parser.getCurrentCommand();
                while (!command.isCommand()) {
                    System.out.println("Unknown command, please try again.");
                    command = parser.getCurrentCommand();
                }
                String c = command.getCommand();
                if (c.equals("play")) {
                    playTurn(currentPlayer);
                    board.printBoard();
                    getPlayerHand(currentPlayer);
                    printScoreboard();
                } else if (c.equals("pass")) {
                    printScoreboard();
                    continue;
                } else if (c.equals("quit")) {
                    printScoreboard();
                    System.out.println("Thanks for playing, goodbye!");
                    gameOn = false;
                    break;
                }
            }
        }
    }

    /**
     * Prints the actions that the player can use
     */
    public void printActions() {
        System.out.println();
        System.out.println("Your command words are: play | pass | quit");
        System.out.println();
    }


    /**
     * Prints the score board
     */
    public void printScoreboard() {
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getPlayerScore() + " pts");
        }
        System.out.println();
    }

    /**
     * Get and display the player's current hand.
     */
    public void getPlayerHand(Player player) {
        player.getHand().printHand(player.getName());
    }

    /**
     * Print game introduction.
     */
    public void printIntro() {
        System.out.println("Welcome to the game of Scrabble!");
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        game.play();
    }
}