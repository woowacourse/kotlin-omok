package omok.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.stone.Stone
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
        omokBoard.placeStone(position, Stone(stoneColor))
        val state = omokBoard.boardState(position)
        assertEquals(positionState, state)
    }

    @Test
    fun `금수자리에는 착수없다`() {
        val omokBoard = OmokBoard()
        val position = Position(1, 1)
        omokBoard.board[position] = PositionState.FORBIDDEN
        assertThrows<IllegalArgumentException> {
            omokBoard.placeStone(position, Stone(StoneColor.BLACK))
        }
    }
}
