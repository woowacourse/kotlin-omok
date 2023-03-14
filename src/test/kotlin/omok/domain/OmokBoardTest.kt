package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class OmokBoardTest {
    @Test
    fun `y의 좌표들을 가지고 있다`() {
        val omokBoard = OmokBoard().keys.map { it.value }
        val expected = (1..15).toList()
        assertThat(omokBoard).isEqualTo(expected)
    }

    @Test
    fun `y의 각자 좌표들에는 OmokLine 이 있다`() {
        val omokBoard = OmokBoard().values
        assertThat(omokBoard).allMatch { omokLine ->
            omokLine.values.filterNot { it == StoneState.EMPTY }.isEmpty()
        }
    }

    @Test
    fun `오목판에 착수 할 수 있다`() {
        var omokBoard = OmokBoard()
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard = omokBoard.placeStone(point, StoneState.BLACK)
        assertThat(omokBoard[point.y][point.x]).isEqualTo(StoneState.BLACK)
    }

    @Test
    fun `오목판에 같은 곳에 착수 할 수 없다`() {
        var omokBoard = OmokBoard()
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard = omokBoard.placeStone(point, StoneState.BLACK)
        assertThrows<IllegalArgumentException> { omokBoard.placeStone(point, StoneState.WHITE) }
    }

    @Test
    fun `오목의 방어적 복사가 된다`() {
        val omokBoard = OmokBoard()
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard.placeStone(point, StoneState.BLACK)
        assertDoesNotThrow { omokBoard.placeStone(point, StoneState.WHITE) }
    }
}
