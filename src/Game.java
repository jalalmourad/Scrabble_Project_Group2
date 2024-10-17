import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class of Scrabble Game
 */
public class Game {

    //Hand playerHand;
    List<Player> players;
    Bag bag;
    Board board;
    Scanner sc;

    /**
     * Create game.
     */
    public Game() {
        //playerHand = new Hand();
        players = new ArrayList<>();
        bag = new Bag();
        board = new Board();
        sc = new Scanner(System.in);
        //playerHand.setLetters(bag.getAlphabet());
    }

    //input the number of players
    public void participants() {
        System.out.println("Provide the number of players participating in the game: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Player " + (i+1) + ": ");
            String playerName = sc.next();
            Player player = new Player(playerName);
            player.getHand().setLetters(bag.getAlphabet());
            players.add(player);
        }
    }

    public void setLetterOnBoard(int x, int y, char letter, Player player) {

       // if(playerHand.getLetters().contains(letter)) {
        Hand playerHand = player.getHand();

                if (playerHand.getLetters().contains(letter)) {
                    System.out.println("Letter Placed is: "+letter+"\n");
                    board.setLetterOnBoard(x, y, letter);
                    playerHand.getLetters().remove(playerHand.getLetterPosition(letter));
                    playerHand.refillHand();
                }

       // }
        else {
                    System.out.println("The letter "+ letter+ " is not in your hand!\n");
                }
    }


    /**
     * Begin game, loops until game is over.
     */
    public void play() {
        printIntro();

        while (true) {
            //board.printBoard();
        }
    }

    /**
     * Print game introduction.
     */
    public void printIntro() {
        System.out.println();
        System.out.println("Welcome to the game of Scrabble!");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    //print the player's hand
    public void getPlayerHand(Player player) {
        player.getHand().printHand(player.getName());
    }

    public Bag getBag(){
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.participants();
        Bag bag = new Bag();
        int i = 0;

        //testing for multiple players
        for (Player currentPlayer : game.players) {
            System.out.println("It's " + currentPlayer.getName() + "'s turn.");

            // Set the letters for the current player's hand
            currentPlayer.getHand().setLetters(bag.getAlphabet());
            game.getPlayerHand(currentPlayer);  // Print the player's hand

            // Simulate making moves and updating the board
            game.setLetterOnBoard(i, 1, 'A', currentPlayer);  // Place 'A'
            game.getPlayerHand(currentPlayer);  // Print the player's hand

            game.setLetterOnBoard(i, 2, 'B', currentPlayer);  // Place 'B'
            game.getPlayerHand(currentPlayer);  // Print the player's hand

            game.setLetterOnBoard(i, 3, 'C', currentPlayer);  // Place 'C'
            game.getPlayerHand(currentPlayer);  // Print the player's hand

            game.setLetterOnBoard(i, 4, 'D', currentPlayer);  // Place 'D'
            game.getPlayerHand(currentPlayer);  // Print the player's hand

            game.setLetterOnBoard(i, 5, 'E', currentPlayer);  // Place 'E'
            game.getPlayerHand(currentPlayer);  // Print the player's hand
            i += 1;

            System.out.println();  // Separate turns with a blank line
        }
//
//        game.playerHand.setLetters(bag.getAlphabet());
//        game.playerHand.printHand();
//
//        game.setLetterOnBoard(1,1,'A');
//        game.playerHand.printHand();
//        game.setLetterOnBoard(1,2,'B');
//        game.playerHand.printHand();
//        game.setLetterOnBoard(1,3,'C');
//        game.playerHand.printHand();
//        game.setLetterOnBoard(1,4,'D');
//        game.playerHand.printHand();
//        game.setLetterOnBoard(1,5,'E');
//        game.playerHand.printHand();
//
//        game.board.printBoard();

//        game.getPlayerHand().setLetters(bag.getAlphabet());
//        game.getPlayerHand().printHand();

//        game.setLetterOnBoard(1,1,'A');
//        game.getPlayerHand().printHand();
//        game.setLetterOnBoard(1,2,'B');
 //       game.getPlayerHand().printHand();
//        game.setLetterOnBoard(1,3,'C');
//        game.getPlayerHand().printHand();
//        game.setLetterOnBoard(1,4,'D');
//        game.getPlayerHand().printHand();
//        game.setLetterOnBoard(1,5,'E');
//        game.getPlayerHand().printHand();

//        while (game.getPlayerHand().getLettersSize()<7){
//            game.playerHand.getLetters().add(bag.addRandomChar());
//        }

        //game.getPlayerHand().printHand();
        game.getBoard().printBoard();


//        game.play();
    }
}
