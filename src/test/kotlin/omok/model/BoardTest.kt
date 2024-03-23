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
        val point = Point(3, 8)

        board.putStone(point)

        assertThat(board.table[8][3] == StoneType.BLACK).isTrue
    }
}
