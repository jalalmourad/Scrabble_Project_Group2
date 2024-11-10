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
    Hand playerHand;

    int xCoordinate;
    int yCoordinate;
    ArrayList<ScrabbleView> views;

    List<int[]> placedPositions;

    List<int[]> removedChars;

    List<int[]> InvalidChars;

    String handListCoord;

    String text;
    private boolean invalidFlag = false;
    boolean done;

    /**
     * Create the game.
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

        parser.loadWordsFromFile("src/Dictionary.txt");
        done = true;
    }

    /**
     * Add views for MVC
     */
    public void addView(ScrabbleView view){
        views.add(view);
    }

    public int getTurn(){
        return turn;
    }

    /**
     * Updates the views for all the subscribed viewers
     */

    public void updateViews(){
        for (ScrabbleView v : views){
            v.update(this);
        }
    }

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
        playerHand = player.getHand();
        if (playerHand.getLetters().contains(letter)) {
            System.out.println("Letter Placed is: "+letter+"\n");
            board.setLetterOnBoard(y,x,letter);
            int letterIndex = playerHand.getLetterPosition(letter);
            removedChars.add(new int[]{letterIndex, letter});
            placedPositions.add(new int[]{x, y});
        } else {
            System.out.println("The letter "+letter+" is not in your hand!\n");
        }
    }

    public void removeCharsFromHand() {
        for (int[] indexLetterPair : removedChars) {
            char letter = (char) indexLetterPair[1];

            if (playerHand.getLetters().contains(letter)) {
                playerHand.getLetters().remove((Character) letter);
            } else {
                System.out.println("Warning: Attempted to remove a letter that doesn't exist in the hand.");
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

    public void clearInvalidWord() {
        for (int[] pos : InvalidChars) {
            int x = pos[0];
            int y = pos[1];
            char letter = board.getLetterOnBoard(y, x);

            // Clear only the current turn's letters on the board
            board.setDeleteLetterFromBoard(y, x, ' ');

            // Return the letter to the player's hand
            getCurrentPlayer().getHand().addLetter(letter);
        }
        InvalidChars.clear();  // Clear after handling the invalid word
        invalidFlag = true;
        updateViews();  // Update the view to show the cleared letters
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


    public void MVCplayTurn(Player currentPlayer, int x, int y, char letter) {

        setLetterOnBoard(y, x, letter, currentPlayer);
        placedPositions.add(new int[]{x, y});
        InvalidChars.add(new int[]{x, y});
        board.printBoard();

    }

    public void setDone(boolean done){
        this.done = done;
    }

    public boolean getDone(){
        return done;
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

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();

    }
}