package cz.mendelu.xmackiv.projekt_2048.projekt_2048;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    static Board testBoard;
    
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }
    
    @BeforeEach
    void setUpTestBoard(){
        testBoard = new Board();
        testBoard.getTiles()[0][0].setTile(2);
        testBoard.getTiles()[0][3].setTile(4);
        testBoard.getTiles()[3][3].setTile(8);
        testBoard.getTiles()[3][0].setTile(16);
        testBoard.getTiles()[1][1].setTile(32);
        testBoard.score = 5;
    }
    
    @Test
    void getTileByDirection() {

        assertEquals(2, testBoard.getTileByDirection(Board.Direction.DOWN, 0,0).getTile());
        assertEquals(4, testBoard.getTileByDirection(Board.Direction.LEFT, 0,0).getTile());
        assertEquals(8, testBoard.getTileByDirection(Board.Direction.UP, 0,0).getTile());
        assertEquals(16, testBoard.getTileByDirection(Board.Direction.RIGHT, 0,0).getTile());

        assertEquals(32, testBoard.getTileByDirection(Board.Direction.DOWN, 1, 1).getTile());
        assertEquals(32, testBoard.getTileByDirection(Board.Direction.UP, 2, 2).getTile());
        assertEquals(32, testBoard.getTileByDirection(Board.Direction.RIGHT, 1, 2).getTile());
        assertEquals(32, testBoard.getTileByDirection(Board.Direction.LEFT, 2, 1).getTile());

        assertEquals(2, testBoard.getTileByDirection(Board.Direction.UP, 3, 3).getTile());
        assertEquals(2, testBoard.getTileByDirection(Board.Direction.RIGHT, 0, 3).getTile());
        assertEquals(2, testBoard.getTileByDirection(Board.Direction.LEFT, 3, 0).getTile());

        assertEquals(4, testBoard.getTileByDirection(Board.Direction.UP, 3, 0).getTile());
        assertEquals(4, testBoard.getTileByDirection(Board.Direction.RIGHT, 3, 3).getTile());
        assertEquals(4, testBoard.getTileByDirection(Board.Direction.DOWN, 0, 3).getTile());

        assertEquals(8, testBoard.getTileByDirection(Board.Direction.DOWN, 3, 3).getTile());
        assertEquals(8, testBoard.getTileByDirection(Board.Direction.RIGHT, 3, 0).getTile());
        assertEquals(8, testBoard.getTileByDirection(Board.Direction.LEFT, 0, 3).getTile());

        assertEquals(16, testBoard.getTileByDirection(Board.Direction.UP, 0, 3).getTile());
        assertEquals(16, testBoard.getTileByDirection(Board.Direction.DOWN, 3, 0).getTile());
        assertEquals(16, testBoard.getTileByDirection(Board.Direction.LEFT, 3, 3).getTile());
    }

    @Test
    void resetGame() {
        testBoard.resetGame();

        assertEquals(0, testBoard.getTiles()[0][0].getTile());
        assertEquals(0, testBoard.getTiles()[1][1].getTile());
        assertEquals(0, testBoard.getScore());
        assertFalse(testBoard.hasWon());
        assertFalse(testBoard.lose);
    }

    @Test
    void canMove() {
        assertTrue(testBoard.canMove());
        int grid_size = testBoard.getGridSize();
        for (int i=0; i<grid_size; i++){
            for(int j=0; j<grid_size; j++){
                testBoard.getTiles()[i][j].setTile(1+i*10+j);
            }
        }
        assertFalse(testBoard.canMove());
    }

    @Test
    void hasWon() {
        assertFalse(testBoard.hasWon());
        testBoard.getTiles()[3][3].setTile(2048);
        assertTrue(testBoard.hasWon());
    }

    @Test
    void action() {
        testBoard.getTiles()[0][1].setTile(2);
        testBoard.action(Board.Direction.RIGHT);
        assertEquals(4, testBoard.getTiles()[0][2].getTile());

    }
}