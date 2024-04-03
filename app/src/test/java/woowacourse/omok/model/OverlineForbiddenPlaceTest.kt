package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.BlackStonePlace
import woowacourse.omok.model.game.OverlinePlace
import woowacourse.omok.model.rule.ban.OverlineForbiddenPlace

class OverlineForbiddenPlaceTest {
    private val forbiddenPlace = OverlineForbiddenPlace()

    @Test
    fun `돌을 두려는 위치로 장목이 되면 놓을 수 없다`() {
        val board =
            initBoard(
                StonePosition(Position(0, 0), Stone.WHITE),
                StonePosition(Position(0, 7), Stone.WHITE),
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
                StonePosition(Position(0, 6), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3), Stone.BLACK)
        assertThat(actual).isInstanceOf(OverlinePlace::class.java)
    }

    @Test
    fun `돌을 두려는 위치로 오목이 되면 놓을 수 있다`() {
        val board =
            initBoard(
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3), Stone.BLACK)
        assertThat(actual).isInstanceOf(BlackStonePlace::class.java)
    }
}
