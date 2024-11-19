import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ScrabbleGame {

    List<Player> players;
    Bag bag;
    Board board;
    Parser parser;
    Scanner sc;
    int turn = 0;
    Hand playerHand;

    int xCoordinate;
    int yCoordinate;
    ArrayList<ScrabbleView> views;

    List<int[]> placedPositions;
    List<int[]> removedChars;
    List<int[]> InvalidChars;

    String handListCoord;

    String text;
    private HashMap<String, Boolean> blankTileLetters;
    private boolean invalidFlag = false;
    boolean gameStarted = false;

    /**
     * Creates the game, initializes players, board, bag, views, etc.
     */
    public ScrabbleGame() {
        players = new ArrayList<>();
        removedChars = new ArrayList<>();
        InvalidChars = new ArrayList<>();
        bag = new Bag();
        board = new Board();
        parser = new Parser();
        sc = new Scanner(System.in);

        views = new ArrayList<>();

        placedPositions = new ArrayList<>();

        blankTileLetters = new HashMap<>();

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
     * Adds players to the game, used by Controller, changes seen in Frame
     */

    public void MVCparticipants(int n, String[] playerTypes) {
        for (int i = 0; i < n; i++) {
            int j = i + 1;
            //String playerName = JOptionPane.showInputDialog("Enter player " + j + " Name: ");
            //Player player = new Player(playerName);
            //player.getHand().setLetters(bag.getAlphabet());
            //players.add(player);
            String playerName;
            if (playerTypes[i].equals("Human")) {
                playerName = JOptionPane.showInputDialog("Enter player " + j + " Name: ");
            } else {
                playerName = "AI " + j;
            }
            Player player = new Player(playerName);
            if (playerTypes[i].equals("AI")) {
                player.setAI(true);
            }
            player.getHand().setLetters(bag.getAlphabet());
            players.add(player);
        }
        gameStarted = true;
    }

    /**
     * Plays the player's turn, used by other classes
     */
    public void MVCplayTurn(Player currentPlayer, int x, int y, char letter) {

        setLetterOnBoard(y, x, letter, currentPlayer);
        placedPositions.add(new int[]{x, y});
        InvalidChars.add(new int[]{x, y});
        board.printBoard();
    }

    /**
     * Set a letter on the board for a player.
     */
    public void setLetterOnBoard(int y, int x, char letter, Player player) {
        playerHand = player.getHand();
        System.out.println("Letter Placed is: "+letter+"\n");
        board.setLetterOnBoard(y,x,letter);
        int letterIndex = playerHand.getLetterPosition(letter);
        removedChars.add(new int[]{letterIndex, letter});
        placedPositions.add(new int[]{x, y});
        //if (playerHand.getLetters().contains(letter)) {
        //    System.out.println("Letter Placed is: "+letter+"\n");
        //    board.setLetterOnBoard(y,x,letter);
        //    int letterIndex = playerHand.getLetterPosition(letter);
        //    removedChars.add(new int[]{letterIndex, letter});
        //    placedPositions.add(new int[]{x, y});
        //} else {
        //    System.out.println("The letter "+letter+" is not in your hand!\n");
        //}
    }

    /**
     * Remove chars from Player's hand
     */
    public void removeCharsFromHand() {
        for (int[] indexLetterPair : removedChars) {
            char letter = (char) indexLetterPair[1];

            if (playerHand.getLetters().contains(letter)) {
                playerHand.getLetters().remove((Character) letter);
            }
        }
        removedChars.clear();
    }

    /**
     * Gets the turn of the current player
     */
    public Player getCurrentPlayer(){
        return players.get(turn%players.size());
    }

    /**
     * Checks if the word placed is a valid word
     */
    public String checkValidWord(int y, int x) {

        StringBuilder word = new StringBuilder();

        for(int i = x; i >= 0 && board.getLetterOnBoard(y,i) != ' '; i--) {
            word.insert(0,board.getLetterOnBoard(y, i));
        }
        for(int i = x + 1; i < 15 && board.getLetterOnBoard(y,i) != ' '; i++) {
            word.append(board.getLetterOnBoard(y,i));
        }
        for(int i = y - 1; i >= 0 && board.getLetterOnBoard(i,x) != ' '; i--) {
            word.insert(0,board.getLetterOnBoard(i,x));
        }
        for(int i = y + 1; i < 15 && board.getLetterOnBoard(i,x) != ' '; i++) {
            word.append(board.getLetterOnBoard(i,x));
        }
        return word.toString();
    }

    /**
     * Checks type of square for each letter placed
     */
    public List<String> checkSquareTypes(int y, int x) {
        ArrayList<String> squareTypes = new ArrayList<>();

        for(int i = x; i >= 0 && board.getLetterOnBoard(y,i) != ' '; i--) {
            squareTypes.add(0, board.getSquareType(y, i));
        }
        for(int i = x + 1; i < 15 && board.getLetterOnBoard(y,i) != ' '; i++) {
            squareTypes.add(board.getSquareType(y,i));
        }
        for(int i= y - 1; i >= 0 && board.getLetterOnBoard(i,x) != ' '; i--) {
            squareTypes.add(0,board.getSquareType(i,x));
        }
        for(int i = y + 1; i < 15 && board.getLetterOnBoard(i,x) != ' '; i++) {
            squareTypes.add(board.getSquareType(i,x));
        }
        return squareTypes;
    }

    /**
     * Clears the invalid word placed by Player
     */
    public void clearInvalidWord() {
        for (int[] pos : InvalidChars) {
            int x = pos[0];
            int y = pos[1];
            char letter = board.getLetterOnBoard(y, x);

            board.setDeleteLetterFromBoard(y, x, ' ');
            getCurrentPlayer().getHand().addLetter(letter);
        }
        InvalidChars.clear();
        invalidFlag = true;
        updateViews();
    }

    /**
     * Following miscellaneous methods are used throughout the program as getters, setters, etc.
     */

    public String getHandListCoord(){
        return handListCoord;
    }

    public void setHandListCoord(String handListCoord){
        this.handListCoord = handListCoord;
    }

    public void setTextPlayed(String text) {
        this.text = text;
    }

    public String getTextPlayed() {
        return text;
    }

    public void addBlankTileLetters(String text, boolean bool) {
        blankTileLetters.put(text, bool);
    }

    public boolean isBlankTileLetter(String text) {
        return blankTileLetters.get(text);
    }

    public boolean invalidFlag() {
        return invalidFlag;
    }

    public void resetInvalidFlag() {
        invalidFlag = false;
    }

    Parser getParser(){
        return parser;
    }

    public boolean getGameStarted() {
        return gameStarted;
    }

    public void setxCoordinate(int xCoordinate){
        this.xCoordinate = xCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    public int getyCoordinate(){
        return yCoordinate;
    }

    public int getTurn(){
        return turn;
    }
}