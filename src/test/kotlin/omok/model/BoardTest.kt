package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `보드에 돌 착수 확인`() {
        val stone = Stone(StoneType.BLACK, Point(3, 8))

        board.putStone(stone)

        assertThat(board.board[3][8] == StoneType.BLACK).isTrue
    }
}
