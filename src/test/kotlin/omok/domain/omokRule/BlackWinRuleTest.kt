package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.adapter.OmokAdapter
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlackWinRuleTest {
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
        0 0 0 0 0 1 1 1 1 0 0 0 0 0 0  8
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
    fun `검은 돌 5개가 가로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(BlackWinRule.validate(board, point)).isTrue
    }
    /*
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  13
        0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  12
        0 0 0 0 1 0 0 0 0 0 0 0 0 0 0  11
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
    fun `검은 돌 5개가 세로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(BlackWinRule.validate(board, point)).isTrue
    }
    /*
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  13
        0 0 0 0 0 0 0 0 1 0 0 0 0 0 0  12
        0 0 0 0 0 0 0 1 0 0 0 0 0 0 0  11
        0 0 0 0 0 0 1 0 0 0 0 0 0 0 0  10
        0 0 0 0 0 1 0 0 0 0 0 0 0 0 0  9
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
    fun `검은 돌 5개가 대각으로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(BlackWinRule.validate(board, point)).isTrue
    }

    /*
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  15
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  14
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  13
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  12
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  11
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  10
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0  9
        0 0 0 0 0 1 1 1 1 1 0 0 0 0 0  8
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
    fun `검은 돌 6개가 연속이면 승리가 아니다`() {

        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        val board = OmokAdapter.adaptBoard(omokBoard)
        val point = OmokAdapter.adaptPoint(OmokPoint('E', 8))
        Assertions.assertThat(BlackWinRule.validate(board, point)).isFalse
    }
}
