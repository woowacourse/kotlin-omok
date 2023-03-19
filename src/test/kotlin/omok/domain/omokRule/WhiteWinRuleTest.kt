package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.adapter.OmokAdapter
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhiteWinRuleTest {
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
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  10
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  9
        0 0 0 0 0 2 2 2 2 2 0 0 0 0 0  8
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  7
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  6
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  5
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  4
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  3
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  2
        1 0 0 0 0 0 0 0 0 0 0 0 0 0 0  1

        A B C D E F G H I J K L M N O
     */
    @Test
    fun `흰 돌 5개 이상 가로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(WhiteWinRule.validate(board, point)).isTrue
    }
    /*
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
        0 0 0 0 2 0 0 0 0 0 0 0 0 0 0  13
        0 0 0 0 2 0 0 0 0 0 0 0 0 0 0  12
        0 0 0 0 2 0 0 0 0 0 0 0 0 0 0  11
        0 0 0 0 2 0 0 0 0 0 0 0 0 0 0  10
        0 0 0 0 2 0 0 0 0 0 0 0 0 0 0  9
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  8
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  7
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  6
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  5
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  4
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  3
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  2
        1 0 0 0 0 0 0 0 0 0 0 0 0 0 0  1

        A B C D E F G H I J K L M N O
     */
    @Test
    fun `흰 돌 5개 이상 세로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 11), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 12), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 13), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(WhiteWinRule.validate(board, point)).isTrue
    }
    /*
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
        0 0 0 0 0 0 0 0 0 2 0 0 0 0 0  13
        0 0 0 0 0 0 0 0 2 0 0 0 0 0 0  12
        0 0 0 0 0 0 0 2 0 0 0 0 0 0 0  11
        0 0 0 0 0 0 2 0 0 0 0 0 0 0 0  10
        0 0 0 0 0 2 0 0 0 0 0 0 0 0 0  9
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  8
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  7
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  6
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  5
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  4
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  3
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  2
        1 0 0 0 0 0 0 0 0 0 0 0 0 0 0  1

        A B C D E F G H I J K L M N O
     */
    @Test
    fun `흰 돌 5개 이상 대각으로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 9), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 10), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 11), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 13), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(WhiteWinRule.validate(board, point)).isTrue
    }
}
