import omok.model.Board
import omok.model.Coordinate
import omok.model.PositionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @ParameterizedTest
    @CsvSource("0, 0", "0, 1", "0, 2")
    fun `오목판 위 빈 공간에 돌을 놓을 수 있다`(
        x: Int,
        y: Int,
    ) {
        val coordinate = Coordinate(x, y)
        board.placeStone(coordinate, PositionType.BLACK_STONE)
        assertEquals(PositionType.BLACK_STONE, board.getBoardLayout()[x][y])
    }

    @Test
    fun `이미 돌이 놓인 자리에 돌을 놓을 수 없다`() {
        val a1 = Coordinate(0, 0)
        board.placeStone(a1, PositionType.BLACK_STONE)
        assertThrows(IllegalArgumentException::class.java) {
            board.placeStone(a1, PositionType.BLACK_STONE)
        }
    }
}
