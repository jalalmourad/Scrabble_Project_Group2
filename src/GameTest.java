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
    public void testSetLetterOnBoard(){
        Board board = new Board();
        board.setLetterOnBoard(0,0, 'A');
        assertEquals(board.getLetterOnBoard(0,0),'A');
    }

    //@Test
    //public void testSetLetterNotInHand() {
    //    ScrabbleGame game = new ScrabbleGame();
    //    Player player = new Player("Ishaq");

    //    game.players.add(player);

    //    player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));

    //    game.setLetterOnBoard(7, 7, 'Z', player);

    //    assertEquals(' ', game.board.getLetterOnBoard(7, 7));
    //}

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

    @Test
    public void testPremiumDoubleLetterSquares() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Kareem");
        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));

        game.setLetterOnBoard(6, 2, 'A', player);
        game.getCurrentPlayer().calculateWordScore(String.valueOf('A'), game.checkSquareTypes(6, 2));

        assertEquals("DoubleLetterSquare", game.board.getSquareType(6,2));
        assertEquals(2, game.getCurrentPlayer().getPlayerScore());
    }

    @Test
    public void testPremiumTripleLetterSquares() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Kareem");
        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G')));

        game.setLetterOnBoard(5, 1, 'B', player);
        game.getCurrentPlayer().calculateWordScore(String.valueOf('B'), game.checkSquareTypes(5, 1));

        assertEquals("TripleLetterSquare", game.board.getSquareType(5,1));
        assertEquals(9, game.getCurrentPlayer().getPlayerScore());
    }

    @Test
    public void testPremiumDoubleWordSquares() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Kareem");
        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('H', 'E', 'L', 'L', 'O', 'A', 'B')));

        game.setLetterOnBoard(1, 0, 'H', player);
        game.setLetterOnBoard(1, 1, 'E', player);
        game.setLetterOnBoard(1, 2, 'L', player);
        game.setLetterOnBoard(1, 3, 'L', player);
        game.setLetterOnBoard(1, 4, 'O', player);

        game.getCurrentPlayer().calculateWordScore(game.checkValidWord(1, 1), game.checkSquareTypes(1, 1));

        assertEquals("DoubleWordSquare", game.board.getSquareType(1,1));
        assertEquals(16, game.getCurrentPlayer().getPlayerScore());
    }

    @Test
    public void testPremiumTripleWordSquares() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Kareem");
        game.players.add(player);

        player.getHand().setLetters(new ArrayList<>(List.of('A', 'A', 'A', 'B', 'C', 'D', 'E')));

        game.setLetterOnBoard(0, 0, 'A', player);
        game.setLetterOnBoard(0, 1, 'A', player);
        game.setLetterOnBoard(0, 2, 'A', player);

        game.getCurrentPlayer().calculateWordScore(game.checkValidWord(0, 0), game.checkSquareTypes(0, 0));

        assertEquals("TripleWordSquare", game.board.getSquareType(0,0));
        assertEquals(9, game.getCurrentPlayer().getPlayerScore());
    }

    @Test
    public void testBlankTile() {
        ScrabbleGame game = new ScrabbleGame();
        Player player = new Player("Kareem");
        game.players.add(player);

        ArrayList<Character> playerHand = new ArrayList<>(List.of(' ', 'B', 'C', 'D', 'E', 'F', 'G'));
        player.getHand().setLetters(playerHand);

        game.addBlankTileLetters(String.valueOf(playerHand.get(0)), true);

        assertTrue(game.isBlankTileLetter(String.valueOf(playerHand.get(0))));
    }

}