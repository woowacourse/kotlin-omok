package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.adapter.RuleAdapter
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FourRuleTest {
    private var omokBoard = OmokBoard()
    private var adapter = RuleAdapter()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    /*
        1 0 0 0
        1 0 0 0
        1 0 0 0
        0 1 1 1
     */
    @Test
    fun `검은 돌은 열린 4가 두개 이상이면 둘 수 없다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        assertThat(adapter.isForbidden(omokBoard, OmokPoint('E', 8))).isTrue
    }

    /*
        1
        1
        1
        0
     */
    @Test
    fun `검은 돌은 열린 4가 한 개면 둘 수 있다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        assertThat(adapter.isForbidden(omokBoard, OmokPoint('E', 8))).isFalse
    }

    /*
        1 0 0 0
        1 0 0 0
        0 1 1 1
     */
    @Test
    fun `검은 돌은 열린 3 4면 둘 수 있다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        assertThat(adapter.isForbidden(omokBoard, OmokPoint('E', 8))).isFalse
    }
}
