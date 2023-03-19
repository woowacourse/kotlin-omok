package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.adapter.OmokAdapter
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ThreeRuleTest {
    private var omokBoard = OmokBoard()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    /*
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  13
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  12
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  11
    0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  10
    0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  9
    0 0 0 0 0 1 1 0 0 0 0 0 0 0 0  8
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  7
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  6
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  5
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  4
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  3
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  2
    2 0 0 0 0 0 0 0 0 0 0 0 0 0 0  1

    A B C D E F G H I J K L M N O
     */
    @Test
    fun `열린 3이 두개 이상이면 둘 수 없다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        assertThat(ThreeRule.validate(board, point)).isTrue
    }

    /*
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  13
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  12
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  11
    0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  10
    0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  9
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  8
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  7
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  6
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  5
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  4
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  3
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  2
    2 0 0 0 0 0 0 0 0 0 0 0 0 0 0  1

    A B C D E F G H I J K L M N O
     */
    @Test
    fun `열린 3이 한 개면 둘 수 있다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        assertThat(ThreeRule.validate(board, point)).isFalse
    }

    /*
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  13
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  12
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  11
    0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  10
    0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  9
    0 0 0 0 0 1 1 1 0 0 0 0 0 0 0  8
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  7
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  6
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  5
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  4
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  3
    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  2
    2 0 0 0 0 0 0 0 0 0 0 0 0 0 0  1

    A B C D E F G H I J K L M N O
     */
    @Test
    fun `열린 3 4면 둘 수 있다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        assertThat(ThreeRule.validate(board, point)).isFalse
    }
}
