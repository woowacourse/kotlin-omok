package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class OmokBoardTest {
    @Test
    fun `오목판에 착수 할 수 있다`() {
        val yLine = YLine()
        var omokBoard = OmokBoard(yLine)
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard = omokBoard.placeStone(point, StoneState.BLACK)
        assertThat(omokBoard[point.y][point.x]).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `오목판에 같은 곳에 착수 할 수 없다`() {
        val yLine = YLine()
        var omokBoard = OmokBoard(yLine)
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard = omokBoard.placeStone(point, StoneState.BLACK)
        assertThrows<IllegalArgumentException> { omokBoard.placeStone(point, StoneState.WHITE) }
    }

    @Test
    fun `오목의 방어적 복사가 된다`() {
        val yLine = YLine()
        val omokBoard = OmokBoard(yLine)
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard.placeStone(point, StoneState.BLACK)
        assertDoesNotThrow { omokBoard.placeStone(point, StoneState.WHITE) }
    }
}
