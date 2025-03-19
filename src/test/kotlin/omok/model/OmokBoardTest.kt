package omok.model

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OmokBoardTest {
    @Test
    fun `초기 오목판은 모든 위치에 착수 가능하다`() {
        val omokBoard = OmokBoard()

        val result = omokBoard.canPlaceStone(Position(X(1), Y(1)))

        assertTrue(result)
    }
}
