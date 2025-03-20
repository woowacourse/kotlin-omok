package omok.model

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.board.X
import omok.model.board.Y
import omok.model.stone.StoneState
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokBoardTest {
    @Test
    fun `초기 모든 위치는 상태가 None이다`() {
        val rule = Omok()
        val board = OmokBoard(rule)

        for (x in 1..15) {
            for (y in 1..15) {
                val positionState = board.board()[Position(X(x), Y(y))]
                Assertions.assertThat(positionState).isEqualTo(StoneState.NONE)
            }
        }
    }

    @CsvSource(value = ["Black", "White"])
    @ParameterizedTest
    fun `초기 오목판은 모든 위치에 착수 가능하다`(stoneStateString: String) {
        val stoneState = StoneState.valueOf(stoneStateString)
        val rule = Omok()
        val omokBoard = OmokBoard(rule)

        val position = Position(X(1), Y(1))
        omokBoard.placeStone(position, stoneState)
        val state = omokBoard.boardState(position)
        assertEquals(stoneState, state)
    }
}
