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

    int xCoordinate;
    int yCoordinate;
    ArrayList<ScrabbleView> views;

    String text;
    boolean done;

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


    Parser getParser(){
        return parser;
    }


    public void MVCplayTurn(Player currentPlayer, int x, int y, char letter) {
        List<int[]> placedPositions = new ArrayList<>();

        if ((x + 1 < 15 && board.getLetterOnBoard(y, x + 1) != ' ') ||
                (y + 1 < 15 && board.getLetterOnBoard(y + 1, x) != ' ') ||
                (x - 1 >= 0 && board.getLetterOnBoard(y, x - 1) != ' ') ||
                (y - 1 >= 0 && board.getLetterOnBoard(y - 1, x) != ' ') ||
                turn == 0) {

            setLetterOnBoard(y, x, letter, currentPlayer);
            placedPositions.add(new int[]{x, y});

            board.printBoard();

        }

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