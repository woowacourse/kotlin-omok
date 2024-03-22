package omok.model

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
    @CsvSource("A1, 0, 0", "A2, 0, 1", "A3, 0, 2")
    fun `오목판 위 빈 공간에 돌을 놓을 수 있다`(
        format: String,
        x: Int,
        y: Int,
    ) {
        val position = Position.from(format)
        board.placeStone(position, BLACK_STONE)
        assertEquals(BLACK_STONE, board.layout[x][y])
    }

    @Test
    fun `이미 돌이 놓인 자리에 돌을 놓을 수 없다`() {
        board.placeStone(A1, BLACK_STONE)
        assertThrows(IllegalStateException::class.java) {
            board.placeStone(A1, BLACK_STONE)
        }
    }
}
