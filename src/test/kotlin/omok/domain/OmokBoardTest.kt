package omok.domain

import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class OmokBoardTest {
    @Test
    fun `처음 생성된 오목판은 빈 오목판이다`() {
        val omokBoard = OmokBoard()
        assertThat(omokBoard.values).allMatch { it is EmptyStoneState }
    }

    @Test
    fun `각 좌표들에는 StoneState가 있다`() {
        val omokBoard = OmokBoard()
        assertThat(omokBoard.values).allMatch { it is StoneState }
    }

    @Test
    fun `오목판에 착수한 후에는 빈 오목판이 아니다`() {
        // given
        var omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)

        // when
        omokBoard = omokBoard.placeStone(point, BlackStoneState)

        // then
        assertThat(omokBoard.values).anyMatch { it !is EmptyStoneState }
    }

    @Test
    fun `생성 당시에 오목을 미리 놓을 수 있다`() {
        // given
        val omokMap = mapOf(
            OmokPoint('A', 1) to BlackStoneState,
            OmokPoint('B', 1) to WhiteStoneState,
            OmokPoint('C', 1) to BlackStoneState,
            OmokPoint('D', 1) to WhiteStoneState,
            OmokPoint('E', 1) to BlackStoneState,
        )

        // when
        val omokBoard = OmokBoard(omokMap)

        // then
        assertAll(
            { assertThat(omokBoard.values.filterIsInstance<BlackStoneState>().size).isEqualTo(3) },
            { assertThat(omokBoard.values.filterIsInstance<WhiteStoneState>().size).isEqualTo(2) },
            { assertThat(omokBoard.values.filterIsInstance<EmptyStoneState>().size).isEqualTo(220) },
        )
    }

    @Test
    fun `좌표를 넣으면 해당 좌표에 StoneState가 있다`() {
        // given
        val omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)

        // when
        val stoneState = omokBoard[point]

        // then
        assertThat(stoneState).isNotNull
    }

    @Test
    fun `오목판에 착수 할 수 있다`() {
        // given
        var omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)

        // when
        omokBoard = omokBoard.placeStone(point, BlackStoneState)

        // then
        assertThat(omokBoard[point]).isEqualTo(BlackStoneState)
    }

    @Test
    fun `오목판에 같은 곳에 착수 할 수 없다`() {
        // given
        var omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)

        // when
        omokBoard = omokBoard.placeStone(point, BlackStoneState)

        // then
        assertThrows<IllegalArgumentException> { omokBoard.placeStone(point, WhiteStoneState) }
    }

    @Test
    fun `오목의 방어적 복사가 된다`() {
        // given
        val omokBoard = OmokBoard()
        val point = OmokPoint('A', 1)

        // when
        omokBoard.placeStone(point, BlackStoneState)

        // then
        assertDoesNotThrow { omokBoard.placeStone(point, WhiteStoneState) }
    }
}
