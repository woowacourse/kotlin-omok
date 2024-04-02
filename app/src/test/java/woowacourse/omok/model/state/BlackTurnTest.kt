package woowacourse.omok.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.state.AlreadyHaveStone
import woowacourse.omok.domain.model.state.BlackTurn
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.WhiteTurn
import woowacourse.omok.model.initBoard

class BlackTurnTest {
    private lateinit var board: Board

    // TODO: 여기서 과연 isEqualTo 를 사용하여 테스트하는 게 맞나? 그러려면 Turn 클래스에서 equals 메서드를 오버라이드해야 할 것 같다.
    @Test
    fun `다음 순서는 백돌의 순서이다`() {
        val blackTurn = BlackTurn(StonePosition(Position(1, 1), Stone.WHITE))
        val actual = blackTurn.nextTurn(Position(1, 2))
        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `이미 돌이 있는 곳에 돌을 두려고 하면 AlreadyHaveStone 상태가 된다`() {
        board =
            initBoard(
                StonePosition(Position(1, 1), Stone.WHITE),
            )
        val blackTurn = BlackTurn(StonePosition(Position(1, 3), Stone.WHITE))
        val actual = blackTurn.place(board, Position(1, 1))

        assertThat(actual).isInstanceOf(AlreadyHaveStone::class.java)
    }

    @Test
    fun `우승하면 Finished 상태가 된다`() {
        board =
            initBoard(
                StonePosition(Position(1, 1), Stone.BLACK),
                StonePosition(Position(1, 2), Stone.BLACK),
                StonePosition(Position(1, 3), Stone.BLACK),
                StonePosition(Position(1, 4), Stone.BLACK),
            )
        val blackTurn = BlackTurn(StonePosition(Position(1, 4), Stone.WHITE))
        val actual = blackTurn.place(board, Position(1, 5))
        assertThat(actual).isEqualTo(Finished(StonePosition(Position(1, 5), Stone.BLACK)))
    }
}
