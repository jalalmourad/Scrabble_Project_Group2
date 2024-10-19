import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testBoard(){
        Board board = new Board();
        board.setLetterOnBoard(0,0, 'A');
        assertEquals(board.getLetterOnBoard(0,0),'A');
    }
    

}