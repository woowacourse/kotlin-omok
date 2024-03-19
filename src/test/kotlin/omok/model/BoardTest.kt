package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `오목 여부를 판단한다`() {
        // given
        val board = Board()
        board.putStone(Position(1, 1))
        board.putStone(Position(2, 2))
        board.putStone(Position(3, 3))
        board.putStone(Position(4, 4))
        board.putStone(Position(5, 5))

        // when
        val actual = board.judgeWinning(Position(3, 3), 1)
        val expected = true

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
