package omok.domain

import omok.OmokBoard
import omok.OmokPoint
import omok.state.BlackStoneState
import omok.state.EmptyStoneState
import omok.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class OmokBoardTest {
    @Test
    fun `각 좌표들에는 StoneState가 있다`() {
        val omokBoard = OmokBoard()
        assertThat(omokBoard.value.values).allMatch { it is EmptyStoneState }
    }

    @Test
    fun `오목판에 착수 할 수 있다`() {
        var omokBoard = OmokBoard()
        val point = OmokPoint(1, 1)

        omokBoard = omokBoard.placeStone(point, BlackStoneState)
        assertThat(omokBoard[point]).isEqualTo(BlackStoneState)
    }

    @Test
    fun `오목판에 같은 곳에 착수 할 수 없다`() {
        var omokBoard = OmokBoard()
        val point = OmokPoint(1, 1)

        omokBoard = omokBoard.placeStone(point, BlackStoneState)

        assertThatThrownBy {
            omokBoard.placeStone(point, WhiteStoneState)
        }
            .message()
            .isEqualTo("(1, 1)는 이미 다른 돌이 있습니다.")
    }

    @Test
    fun `오목의 방어적 복사가 된다`() {
        val omokBoard = OmokBoard()
        val point = OmokPoint(1, 1)

        omokBoard.placeStone(point, BlackStoneState)
        assertDoesNotThrow { omokBoard.placeStone(point, WhiteStoneState) }
    }
}
