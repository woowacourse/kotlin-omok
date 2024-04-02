package woowacourse.omok.model.state

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.state.AlreadyHaveStone
import woowacourse.omok.domain.model.state.BlackTurn
import woowacourse.omok.domain.model.state.ForbiddenPosition
import woowacourse.omok.model.initBoard
import woowacourse.omok.model.state.StateTestFixture.blackStoneRenjuRule

class InvalidPositionTest {
    private val board = initBoard()
    private val testState = BlackTurn(StonePosition(Position(1, 1), Stone.WHITE), blackStoneRenjuRule)

    @Test
    fun `유효하지 않은 위치에서 돌을 둘 수 없다`() {
        val alreadyHaveStone =
            AlreadyHaveStone(
                StonePosition(Position(1, 1), Stone.BLACK),
                testState,
            )
        assertThrows<IllegalStateException> {
            alreadyHaveStone.place(board, Position(1, 1))
        }
    }

    @Test
    fun `금수 상태 위치에서 돌을 둘 수 없다`() {
        val forbiddenPosition =
            ForbiddenPosition(
                StonePosition(Position(1, 1), Stone.BLACK),
                testState,
            )
        assertThrows<IllegalStateException> {
            forbiddenPosition.place(board, Position(1, 1))
        }
    }
}
