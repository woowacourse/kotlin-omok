package woowacourse.omok.model.state

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.state.AlreadyHaveStone
import woowacourse.omok.domain.model.state.BlackTurn
import woowacourse.omok.domain.model.state.WhiteTurn
import woowacourse.omok.model.initBoard

class WhiteTurnTest {
    private lateinit var board: Board

    @Test
    fun `다음 순서는 흑돌의 순서이다`() {
        val whiteTurn = WhiteTurn(StonePosition(Position(1, 1), Stone.WHITE))
        val actual = whiteTurn.nextTurn(Position(1, 2))
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `이미 돌이 있는 곳에 돌을 두려고 하면 AlreadyHaveStone 상태가 된다`() {
        board =
            initBoard(
                StonePosition(Position(1, 1), Stone.BLACK),
            )
        val whiteTurn = WhiteTurn(StonePosition(Position(1, 3), Stone.WHITE))
        val actual = whiteTurn.place(board, Position(1, 1))

        Assertions.assertThat(actual).isInstanceOf(AlreadyHaveStone::class.java)
    }
}
