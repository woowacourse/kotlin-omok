package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.adapter.RuleAdapter
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhiteWinRuleTest {
    private var omokBoard = OmokBoard()
    private var adapter = RuleAdapter()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    /*
       0 2 2 2 2 2
     */
    @Test
    fun `흰 돌 5개 이상 가로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        assertThat(adapter.isWin(omokBoard, OmokPoint('E', 8), WhiteStoneState)).isTrue
    }
    /*
        2
        2
        2
        2
        2
        0
     */
    @Test
    fun `흰 돌 5개 이상 세로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 11), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 12), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 13), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        assertThat(adapter.isWin(omokBoard, OmokPoint('E', 8), WhiteStoneState)).isTrue
    }
    /*
        0 0 0 0 0 2
        0 0 0 0 2 0
        0 0 0 2 0 0
        0 0 2 0 0 0
        0 2 0 0 0 0
        0 0 0 0 0 0
     */
    @Test
    fun `흰 돌 5개 이상 대각으로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 9), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 10), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 11), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 13), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        assertThat(adapter.isWin(omokBoard, OmokPoint('E', 8), WhiteStoneState)).isTrue
    }
}
