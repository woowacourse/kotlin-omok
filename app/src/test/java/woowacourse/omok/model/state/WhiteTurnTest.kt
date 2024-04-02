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
import woowacourse.omok.model.state.StateTestFixture.blackStoneRenjuRule
import woowacourse.omok.model.state.StateTestFixture.whiteStoneRenjuRule

class WhiteTurnTest {
    private lateinit var board: Board

    // TODO: 여기서 과연 isEqualTo 를 사용하여 테스트하는 게 맞나? 그러려면 Turn 클래스에서 equals 메서드를 오버라이드해야 할 것 같다.
    @Test
    fun `다음 순서는 흑돌의 순서이다`() {
        val whiteTurn = WhiteTurn(StonePosition(Position(1, 1), Stone.WHITE), whiteStoneRenjuRule)
        val actual = whiteTurn.nextTurn(Position(1, 2), blackStoneRenjuRule)
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `이미 돌이 있는 곳에 돌을 두려고 하면 AlreadyHaveStone 상태가 된다`() {
        board =
            initBoard(
                StonePosition(Position(1, 1), Stone.BLACK),
            )
        val whiteTurn = WhiteTurn(StonePosition(Position(1, 3), Stone.WHITE), whiteStoneRenjuRule)
        val actual = whiteTurn.place(board, Position(1, 1))

        Assertions.assertThat(actual).isInstanceOf(AlreadyHaveStone::class.java)
    }
}
