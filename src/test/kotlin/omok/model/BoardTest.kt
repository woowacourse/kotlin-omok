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

        board.putStone(point, BlackTurn(BlackRule()))

        assertThat(board.table[8][3] == StoneType.BLACK).isTrue
    }

    @Test
    fun `보드는 이전 차례에서 둔 돌의 위치를 알고있다`() {
        board.putStone(
            Point(0, 0),
            BlackTurn(BlackRule()),
        )

        val actual = board.beforePoint

        assertThat(actual).isEqualTo(Point(0, 0))
    }
}
