package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.player.GameState
import woowacourse.omok.model.player.Turn
import woowacourse.omok.model.stone.StoneColor

class TurnTest {
    @Test
    fun `현재 흑돌의 차례일 경우 다음 차례는 백돌이다`() {
        val turn = Turn()
        turn.next()
        val stone = turn.stone

        assertThat(stone.color).isEqualTo(StoneColor.WHITE)
    }

    @Test
    fun `같은 돌이 연속 5개일 때 승리한다`() {
        val turn = Turn()
        val omokBoard = OmokBoard()
        winPositions.forEach { position ->
            omokBoard.board[position] = PositionState.BLACK_POSITION
        }

        val gameState = turn.place(Position(1, 5), omokBoard)
        assertThat(gameState).isEqualTo(GameState.Win)
    }

    @Test
    fun `금수인 위치에는 착수할 수 없다`() {
        val turn = Turn()
        val omokBoard = OmokBoard()

        forbiddenPositions.forEach { position ->
            omokBoard.board[position] = PositionState.BLACK_POSITION
        }

        assertThat(turn.place(Position(4, 12), omokBoard)).isEqualTo(GameState.ForbiddenMove)
    }

    companion object {
        private val winPositions =
            listOf(
                Position(1, 1),
                Position(1, 2),
                Position(1, 3),
                Position(1, 4),
            )

        private val forbiddenPositions =
            listOf(
                Position(3, 12),
                Position(5, 12),
                Position(4, 13),
                Position(4, 14),
            )
    }
}
