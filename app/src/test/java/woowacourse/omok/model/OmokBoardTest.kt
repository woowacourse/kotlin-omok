package omok.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.player.GameState
import woowacourse.omok.model.player.Turn
import woowacourse.omok.model.stone.StoneColor

class OmokBoardTest {
    @Test
    fun `초기 모든 위치는 상태가 None이다`() {
        val board = OmokBoard()

        for (x in 1..15) {
            for (y in 1..15) {
                val positionState = board.board[Position(x, y)]
                Assertions.assertThat(positionState).isEqualTo(PositionState.NONE)
            }
        }
    }

    @CsvSource(value = ["BLACK_POSITION,BLACK", "WHITE_POSITION,WHITE"])
    @ParameterizedTest
    fun `초기 오목판은 모든 위치에 착수 가능하다`(
        positionStateString: String,
        stoneColorString: String,
    ) {
        val positionState = PositionState.valueOf(positionStateString)
        val stoneColor = StoneColor.valueOf(stoneColorString)
        val omokBoard = OmokBoard()

        val position = Position(1, 1)
        omokBoard.placeStone(position, stoneColor)
        val state = omokBoard.boardState(position)
        assertEquals(positionState, state)
    }

    @Test
    fun `금수자리에는 착수할 수 없다`() {
        val omokBoard = OmokBoard()
        val stoneColor = StoneColor.BLACK
        val position = Position(1, 1)
        omokBoard.placeStone(position, stoneColor)
        makeForbiddenStones(omokBoard)
        assertEquals(omokBoard.board[Position(5, 4)], PositionState.FORBIDDEN)
    }

    companion object {
        fun makeForbiddenStones(omokBoard: OmokBoard): GameState {
            val turn = Turn()
            turn.place(Position(5, 2), omokBoard)
            turn.place(Position(5, 3), omokBoard)
            turn.place(Position(4, 4), omokBoard)
            turn.place(Position(6, 4), omokBoard)
            return turn.place(Position(5, 4), omokBoard)
        }
    }
}
