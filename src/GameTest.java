import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testBoard(){
        Board board = new Board();
        board.setLetterOnBoard(0,0, 'A');
        assertEquals(board.getLetterOnBoard(0,0),'A');
    }

    @Test
    public void checkPlayerHand(){

        Player player = new Player("TestPlayer");
        assertNotNull(player.getHand());
    }

    @Test
    public void testSizeofHand(){
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");
        game.players.add(player);
        assertEquals(7,player.getHand().getLettersSize());
    }

    @Test
    public void checkBoardEmpty(){

        Board board = new Board();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                assertEquals(' ', board.getLetterOnBoard(i,j));
            }
        }

    }

    @Test
    public void testFirstLetter(){
        Player player = new Player("TestPlayer");
        ScrabbleGame game = new ScrabbleGame();
        game.bag = new Bag();

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));
        game.setLetterOnBoard(7,7,player.getHand().getLetters().get(0),player);
        assertEquals('A',game.board.getLetterOnBoard(7,7));


    }

    @Test
    public void testGameInitialization() {
        ScrabbleGame game = new ScrabbleGame();

        assertNotNull(game.bag);
        assertNotNull(game.board);
        assertNotNull(game.parser);

        assertEquals(0, game.players.size());
    }

    @Test
    public void testParticipants() {
        ScrabbleGame game = new ScrabbleGame();

        // Simulate adding 2 players (mocking user input)
        Scanner scanner = new Scanner("2\nIshaq\nNour\n");
        game.sc = scanner;
        //game.participants();

        // Check that 2 players were added
        assertEquals(2, game.players.size());
        assertEquals("Ishaq", game.players.get(0).getName());
        assertEquals("Nour", game.players.get(1).getName());
    }

    @Test
    public void testInvalidParticipants() {
        ScrabbleGame game = new ScrabbleGame();

        Scanner scanner = new Scanner("1\n5\n3\nIshaq\nNour\nMoe\n");
        game.sc = scanner;
       // game.participants();

        assertEquals(3, game.players.size());
        assertEquals("Ishaq", game.players.get(0).getName());
        assertEquals("Nour", game.players.get(1).getName());
        assertEquals("Moe", game.players.get(2).getName());
    }

    @Test
    public void testSetLetterOnBoard(){
        Board board = new Board();
        board.setLetterOnBoard(0,0, 'A');
        assertEquals(board.getLetterOnBoard(0,0),'A');
    }

    @Test
    public void testSetLetterNotInHand() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));

        game.setLetterOnBoard(7, 7, 'Z', player);

        assertEquals(' ', game.board.getLetterOnBoard(7, 7));
    }

    @Test
    public void testMultipleLettersOnBoard() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('H', 'E', 'L', 'L', 'O')));

        game.setLetterOnBoard(7, 7, 'H', player);
        game.setLetterOnBoard(7, 8, 'E', player);
        game.setLetterOnBoard(7, 9, 'L', player);
        game.setLetterOnBoard(7, 10, 'L', player);
        game.setLetterOnBoard(7, 11, 'O', player);

        String formedWord = game.checkValidWord(7, 7);
        assertEquals("HELLO", formedWord);

        assertEquals('H', game.board.getLetterOnBoard(7, 7));
        assertEquals('E', game.board.getLetterOnBoard(7, 8));
        assertEquals('L', game.board.getLetterOnBoard(7, 9));
        assertEquals('L', game.board.getLetterOnBoard(7, 10));
        assertEquals('O', game.board.getLetterOnBoard(7, 11));
    }

    @Test
    public void checkHandRefill() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");
        game.players.add(player);


        ArrayList<Character> startingHand = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G'));
        player.getHand().setLetters(startingHand);
        System.out.println("Hand before: " + player.getHand().getLetters());

        assertEquals(7, player.getHand().getLetters().size());

        game.setLetterOnBoard(7, 7, 'A', player);
        System.out.println("Hand after: " + player.getHand().getLetters());

        assertNotEquals('A', player.getHand().getLetters().get(0));
        assertEquals(7, player.getHand().getLetters().size());
    }

    @Test
    public void testWord() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('H', 'E', 'L', 'L', 'O')));

        game.setLetterOnBoard(7, 7, 'H', player);
        game.setLetterOnBoard(7, 8, 'E', player);
        game.setLetterOnBoard(7, 9, 'L', player);
        game.setLetterOnBoard(7, 10, 'L', player);
        game.setLetterOnBoard(7, 11, 'O', player);

        String formedWord = game.checkValidWord(7, 7);

        assertEquals("HELLO", formedWord);
    }

    @Test
    public void testInvalidWord() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('I','I','I','S', 'H', 'A','Q')));

        game.setLetterOnBoard(7, 7, 'I', player);
        game.setLetterOnBoard(7, 8, 'S', player);
        game.setLetterOnBoard(7, 9, 'H', player);
        game.setLetterOnBoard(7, 10, 'A', player);
        game.setLetterOnBoard(7, 11, 'Q', player);

        String formedWord = game.checkValidWord(7, 7);

        assertEquals("ISHAQ", formedWord);
        boolean isValid = game.parser.isValidWord(formedWord);
        assertFalse(isValid);
    }


    @Test
    public void testPlayTurn() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");
        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));
        Scanner scanner = new Scanner("A\n7\n7\nno\n");
        game.sc = scanner;
      //  game.playTurn(player);

        assertEquals('A', game.board.getLetterOnBoard(7, 7));
        assertEquals(7, player.getHand().getLettersSize());
    }

    @Test
    public void testGetPlayerHand() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Ishaq");
        game.players.add(player);

        ArrayList<Character> playerHand = new ArrayList<>(List.of('I','I','I','S', 'H', 'A','Q'));
        player.getHand().setLetters(playerHand);
       // game.getPlayerHand(player);

        assertEquals(playerHand, player.getHand().getLetters());
        assertEquals(7, player.getHand().getLettersSize());
    }

}