import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void checkPlayerHandSize(){
        Player player = new Player("TestPlayer");
        assertEquals(player.getHand().getLettersSize(),7);
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
    public void firstLetterPlacement(){
        Player player = new Player("TestPlayer");
        Game game = new Game();
        
        player.getHand().setLetters(new ArrayList<>(List.of('A')));
        game.setLetterOnBoard(7,7,player.getHand().getLetters().get(0),player);
        assertEquals('A',game.board.getLetterOnBoard(7,7));


    }
    

}