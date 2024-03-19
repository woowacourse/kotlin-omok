package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `오목 여부를 판단한다 - 오목일 때`() {
        // given
        val board = Board()
        board.putStone(Position(1, 1), Stone.BLACK_STONE)
        board.putStone(Position(2, 2), Stone.BLACK_STONE)
        board.putStone(Position(3, 3), Stone.BLACK_STONE)
        board.putStone(Position(4, 4), Stone.BLACK_STONE)
        board.putStone(Position(5, 5), Stone.BLACK_STONE)

        // when
        val actual = board.judgeWinning(Position(3, 3), Stone.BLACK_STONE)
        val expected = true

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목 여부를 판단한다 - 오목이 아닐 때`() {
        // given
        val board = Board()
        board.putStone(Position(1, 1), Stone.BLACK_STONE)
        board.putStone(Position(2, 2), Stone.BLACK_STONE)
        board.putStone(Position(3, 3), Stone.WHITE_STONE)
        board.putStone(Position(4, 4), Stone.BLACK_STONE)
        board.putStone(Position(5, 5), Stone.BLACK_STONE)

        // when
        val actual = board.judgeWinning(Position(3, 3), Stone.BLACK_STONE)
        val expected = false

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
