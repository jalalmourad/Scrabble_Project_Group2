import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    List<Player> players;
    Bag bag;
    Board board;
    private Parser parser;
    Scanner sc;

    /**
     * Create the game.
     */
    public Game() {
        players = new ArrayList<>();
        bag = new Bag();
        board = new Board();
        parser = new Parser();
        sc = new Scanner(System.in);

        parser.loadWordsFromFile("src/Dictionary.txt");
    }

    /**
     * Add players to the game.
     */
    public void participants() {
        System.out.println("Provide the number of players participating in the game: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Player "+(i+1)+": ");
            String playerName = sc.next();
            Player player = new Player(playerName);
            player.getHand().setLetters(bag.getAlphabet());
            players.add(player);
        }
    }

    /**
     * Set a letter on the board for a player.
     */
    public void setLetterOnBoard(int x, int y, char letter, Player player) {
        Hand playerHand = player.getHand();

        if (playerHand.getLetters().contains(letter)) {
            System.out.println("Letter Placed is: "+letter+"\n");
            board.setLetterOnBoard(x,y,letter);
            playerHand.getLetters().remove(playerHand.getLetterPosition(letter));
            playerHand.refillHand();
        } else {
            System.out.println("The letter "+letter+" is not in your hand!\n");
        }
    }


    public void playTurn(Player currentPlayer) {


        List<Character> placedLetters = new ArrayList<>();
        boolean turnOver = false;
        while (!turnOver) {
            System.out.print("Enter the X coordinate: ");
            int x = sc.nextInt();
            System.out.print("Enter the Y coordinate: ");
            int y = sc.nextInt();
            System.out.print("Enter the letter to place: ");
            char letter = sc.next().charAt(0);
            setLetterOnBoard(x, y, letter, currentPlayer);
            placedLetters.add(letter);
            System.out.print("Do you want to place another letter? (yes/no): ");
            String placeAnother = sc.next();
            if (placeAnother.equalsIgnoreCase("no")) {
                turnOver = true;
            }

        }

        String formedWord = formWordFromPlacedLetters(placedLetters);

        if (parser.isValidWord(formedWord)) {
            System.out.println(formedWord+" is a valid word!");
        } else {
            System.out.println(formedWord+" is not a valid English word!");
        }
    }


    public String formWordFromPlacedLetters(List<Character> placedLetters) {
        StringBuilder word = new StringBuilder();
        for (char letter : placedLetters) {
            word.append(letter);
        }
        return word.toString();
    }


    public void play() {
        printIntro();
        participants();

        boolean gameOn = true;
        while (gameOn) {
            for (Player currentPlayer : players) {
                System.out.println("It's "+currentPlayer.getName()+"'s turn.");
                getPlayerHand(currentPlayer);
                playTurn(currentPlayer);
                board.printBoard();
                getPlayerHand(currentPlayer);
                System.out.print("Continue playing? (yes/no): ");
                String continueGame = sc.next();
                if (continueGame.equalsIgnoreCase("no")) {
                    gameOn = false;
                    break;
                }}
        }
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
        Game game = new Game();
        game.play();
    }
}
