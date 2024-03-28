package woowacourse.omok.kotlin.omok.model.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.kotlin.omok.model.resetBoard
import woowacourse.omok.kotlin.omok.model.resetPosition
import woowacourse.omok.src.main.kotlin.omok.model.board.Board
import woowacourse.omok.src.main.kotlin.omok.model.position.Position
import woowacourse.omok.src.main.kotlin.omok.model.stone.BlackStone

class BoardTest {
    @BeforeEach
    fun setUp() {
        resetBoard()
        resetPosition(Board, "lastPosition")
    }

    @Test
    fun `마지막 돌이 없다면 null을 반환한다`() {
        val actual = Board.getLastStonePosition()
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 돌이 있다면 돌의 위치를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.getLastStonePosition()
        val expected = Position(1, 5)
        assertThat(actual).isEqualTo(expected)
    }
}
