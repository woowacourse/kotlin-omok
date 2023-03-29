package omok.domain.omokRule

import omok.OmokBoard
import omok.OmokPoint
import omok.omokRule.adapter.Referee
import omok.state.BlackStoneState
import omok.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FourRenjuRuleTest {
    private var omokBoard = OmokBoard()
    private var referee = Referee()

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
        omokBoard = omokBoard.placeStone(OmokPoint(5, 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(6, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(7, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(8, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isForbidden(omokBoard, OmokPoint(5, 8))).isTrue
    }

    /*
        1
        1
        1
        0
     */
    @Test
    fun `검은 돌은 열린 4가 한 개면 둘 수 있다`() {
        omokBoard = omokBoard.placeStone(OmokPoint(5, 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isForbidden(omokBoard, OmokPoint(5, 8))).isFalse
    }

    /*
        1 0 0 0
        1 0 0 0
        0 1 1 1
     */
    @Test
    fun `검은 돌은 열린 3 4면 둘 수 있다`() {
        omokBoard = omokBoard.placeStone(OmokPoint(5, 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(6, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(7, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(8, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isForbidden(omokBoard, OmokPoint(5, 8))).isFalse
    }
}
