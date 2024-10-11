/**
 * Main class of Scrabble Game
 */
public class Game {

    /**
     * Create game.
     */
    public Game() {
        initGame();
    }

    /**
     * Initialize game; setup board and players.
     */
    public void initGame() {
        Board board = new Board();
        // Create new players here
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



    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
