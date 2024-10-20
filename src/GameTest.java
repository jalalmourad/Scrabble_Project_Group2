import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testGameInitialization() {
        Game game = new Game();

        assertNotNull(game.bag);
        assertNotNull(game.board);
        assertNotNull(game.parser);

        assertEquals(0, game.players.size());
    }

    @Test
    public void testParticipants() {
        Game game = new Game();

        // Simulate adding 2 players (mocking user input)
        Scanner scanner = new Scanner("2\nIshaq\nNour\n");
        game.sc = scanner;
        game.participants();

        // Check that 2 players were added
        assertEquals(2, game.players.size());
        assertEquals("Ishaq", game.players.get(0).getName());
        assertEquals("Nour", game.players.get(1).getName());
    }

    @Test
    public void testParticipants2() {
        Game game = new Game();

        Scanner scanner = new Scanner("1\n5\n3\nIshaq\nNour\nMoe\n");
        game.sc = scanner;
        game.participants();

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
        Game game = new Game();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));

        game.setLetterOnBoard(7, 7, 'Z', player);

        assertEquals(' ', game.board.getLetterOnBoard(7, 7));
    }

    @Test
    public void testSetMultipleLettersAndFormWord() {
        Game game = new Game();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('H', 'E', 'L', 'L', 'O')));

        game.setLetterOnBoard(7, 7, 'H', player);
        game.setLetterOnBoard(7, 8, 'E', player);
        game.setLetterOnBoard(7, 9, 'L', player);
        game.setLetterOnBoard(7, 10, 'L', player);
        game.setLetterOnBoard(7, 11, 'O', player);

        List<Character> placedLetters = List.of('H', 'E', 'L', 'L', 'O');
        String formedWord = game.formWordFromPlacedLetters(placedLetters);

        assertEquals("HELLO", formedWord);

        assertEquals('H', game.board.getLetterOnBoard(7, 7));
        assertEquals('E', game.board.getLetterOnBoard(7, 8));
        assertEquals('L', game.board.getLetterOnBoard(7, 9));
        assertEquals('L', game.board.getLetterOnBoard(7, 10));
        assertEquals('O', game.board.getLetterOnBoard(7, 11));
    }

    @Test
    public void testHandRefill() {
        Game game = new Game();
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
    public void testFormWordFromPlacedLetters() {
        Game game = new Game();

        List<Character> placedLetters = List.of('H', 'E', 'L', 'L', 'O');
        String formedWord = game.formWordFromPlacedLetters(placedLetters);

        assertEquals("HELLO", formedWord);
    }

    @Test
    public void testFormingInvalidWord() {
        Game game = new Game();
        Player player = new Player("Ishaq");

        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('I', 'S', 'H', 'A','Q')));

        game.setLetterOnBoard(7, 7, 'I', player);
        game.setLetterOnBoard(7, 8, 'S', player);
        game.setLetterOnBoard(7, 9, 'H', player);
        game.setLetterOnBoard(7, 10, 'A', player);
        game.setLetterOnBoard(7, 11, 'Q', player);

        List<Character> placedLetters = List.of('I', 'S', 'H', 'A','Q');
        String formedWord = game.formWordFromPlacedLetters(placedLetters);

        assertEquals("ISHAQ", formedWord);

        boolean isValid = game.parser.isValidWord(formedWord);

        assertFalse(isValid);
    }

    @Test
    public void testPlayTurn() {
        Game game = new Game();
        Player player = new Player("Ishaq");
        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C')));
        Scanner scanner = new Scanner("7\n7\nA\nno\n");
        game.sc = scanner;

        game.playTurn(player);

        assertEquals('A', game.board.getLetterOnBoard(7, 7));
    }

    @Test
    public void testGetPlayerHand() {
        Game game = new Game();
        Player player = new Player("Ishaq");
        game.players.add(player);

        ArrayList<Character> playerHand = new ArrayList<>(List.of('I','I','I', 'S', 'H', 'A','Q'));
        player.getHand().setLetters(playerHand);
        game.getPlayerHand(player);

        assertEquals(playerHand, player.getHand().getLetters());
        assertEquals(7, player.getHand().getLettersSize());
    }

}