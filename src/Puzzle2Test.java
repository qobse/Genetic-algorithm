import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Puzzle2Test {

    @Test
    void testInitializeBoard() {
        Puzzle2 puzzle = new Puzzle2(4);
        assertNotNull(puzzle.getBoard());
        assertEquals(4, puzzle.getBoard().length);
        assertEquals(4, puzzle.getBoard()[0].length);
    }

    @Test
    void testLoadPuzzleFromFile() throws Exception {
        Puzzle2 puzzle = new Puzzle2("puzzle_input.txt"); // Replace with actual file path
        assertNotNull(puzzle.getBoard());
        assertEquals(3, puzzle.getBoard().length);
        assertEquals(3, puzzle.getBoard()[0].length);
    }

    @Test
    void testFitness() {
        Puzzle2 puzzle = new Puzzle2(4);
        int fitness = puzzle.fitness();
        // The fitness should be a non-positive value as the board is initialized randomly.
        assertTrue(fitness <= 0);
    }

    @Test
    void testGetBoard() {
        Puzzle2 puzzle = new Puzzle2(4);
        assertNotNull(puzzle.getBoard());
    }

    @Test
    void testSetGrid() {
        Puzzle2 puzzle = new Puzzle2(4);
        puzzle.setGrid(0, 0, 5);
        assertEquals(5, puzzle.getBoard()[0][0].getValue());
    }

    @Test
    void testGetGameSize() {
        Puzzle2 puzzle = new Puzzle2(4);
        assertEquals(4, puzzle.getGameSize());
    }

    @Test
    void testPrintBoard() {
        Puzzle2 puzzle = new Puzzle2(4);
        // Assert that the printBoard method doesn't throw any exceptions
        assertDoesNotThrow(puzzle::printBoard);
    }
}

