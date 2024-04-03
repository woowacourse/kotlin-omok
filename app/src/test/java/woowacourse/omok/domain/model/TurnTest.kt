package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.WhiteTurn

class TurnTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `흑돌 차례 다음은 백돌 차례이다`() {
        val turn = BlackTurn()
        val actual = turn.putStone(Point(0, 0), board)

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백돌 차례 다음은 흑돌 차례이다`() {
        val turn = WhiteTurn()
        val actual = turn.putStone(Point(0, 0), board)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }
}
