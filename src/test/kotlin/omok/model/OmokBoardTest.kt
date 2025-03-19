package omok.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokBoardTest {
    @CsvSource(value = ["Black", "White"])
    @ParameterizedTest
    fun `초기 오목판은 모든 위치에 착수 가능하다`(stoneStateString: String) {
        val stoneState = StoneState.valueOf(stoneStateString)
        val omokBoard = OmokBoard()

        val position = Position(X(1), Y(1))
        omokBoard.placeStone(position, stoneState)
        val state = omokBoard.boardState(position)
        assertEquals(stoneState, state)
    }
}
