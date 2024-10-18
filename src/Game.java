import java.util.ArrayList;
import java.util.List;

/**
 * Main class of Scrabble Game
 */
public class Game {

    List<Player> players;
    Hand playerHand;
    Bag bag;
    Board board;
    private Parser parser;
    //Player player;

    /**
     * Create game.
     */
    public Game() {
        players = new ArrayList<>();

        playerHand = new Hand(); // MOVE INTO PLAYER
        bag = new Bag();
        board = new Board();
        parser = new Parser();
        //playerHand.setLetters(bag.getAlphabet());
    }

    public void setLetterOnBoard(int x, int y, char letter){

       // if(playerHand.getLetters().contains(letter)) {

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


    private boolean processCommand(Command command) {
        boolean quit = false;

        if (!command.isCommand()) {
            System.out.println("Unknown command, please try again.");
            return false;
        }

        String c = command.getCommand();
        if (c.equals("play")) {
            // PLAYER GETS THEIR TURN
        } else if (c.equals("pass")) {
            // PLAYER CAN FORFEIT THEIR TURN, GOES TO NEXT PLAYER
        } else if (c.equals("quit")) {
            quit = true;
        } else if (c.equals("help")) {
            printHelp();
        }
        return quit;
    }

    /**
     * Begin game, loops until game is over.
     */
    public void play() {
        printIntro();

        boolean gameDone = false;
        while (!gameDone) {
            Command command = parser.getCurrentCommand();
            gameDone = processCommand(command);
        }
        System.out.println("Thanks for playing!");
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Bag getBag(){
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Print game introduction.
     */
    public void printIntro() {
        System.out.println();
        System.out.println("Welcome to the game of Scrabble!");
        System.out.println("Please enter how many players you would like to have, with a minimum of 2:");
        System.out.println();
    }

    /**
     * Print game help.
     */
    public void printHelp() {
        System.out.println();
        System.out.println("Options:");
        System.out.println("play: play your turn, place a letter on the board.");
        System.out.println("pass: forfeit your turn, allow the next player to play.");
        System.out.println("quit: End the game, automatically results in everyone's loss.");
        System.out.println();
    }

    public static void main(String[] args) {
        Game game = new Game();
        Bag bag = new Bag();
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

        game.getPlayerHand().setLetters(bag.getAlphabet());
        game.getPlayerHand().printHand();

        game.setLetterOnBoard(1,1,'A');
        game.getPlayerHand().printHand();
        game.setLetterOnBoard(1,2,'B');
        game.getPlayerHand().printHand();
        game.setLetterOnBoard(1,3,'C');
        game.getPlayerHand().printHand();
        game.setLetterOnBoard(1,4,'D');
        game.getPlayerHand().printHand();
        game.setLetterOnBoard(1,5,'E');
        game.getPlayerHand().printHand();

//        while (game.getPlayerHand().getLettersSize()<7){
//            game.playerHand.getLetters().add(bag.addRandomChar());
//        }

        //game.getPlayerHand().printHand();
        game.getBoard().printBoard();


//        game.play();
    }
}
