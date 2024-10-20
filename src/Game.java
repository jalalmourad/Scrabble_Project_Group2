import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    List<Player> players;
    Bag bag;
    Board board;
    Parser parser;
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
            setLetterOnBoard(y, x, letter, currentPlayer);
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
                } else if (c.equals("pass")) {
                    continue;
                } else if (c.equals("quit")) {
                    System.out.println("Thanks for playing, goodbye!");
                    gameOn = false;
                    break;
                }
            }
        }
    }

    public void printActions() {
        System.out.println();
        System.out.println("Your command words are: play | pass | quit");
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
