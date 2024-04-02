package woowacourse.omok.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.state.InitialGameTurn
import woowacourse.omok.domain.model.state.WhiteTurn
import woowacourse.omok.model.initBoard

class InitialGameTurnTest {
    private val board = initBoard()

    @Test
    fun `돌을 두면 흼 돌의 차례가 된다`() {
        val initialGameTurn = InitialGameTurn()
        val actual = initialGameTurn.place(board, Position(1, 1))
        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }
}
