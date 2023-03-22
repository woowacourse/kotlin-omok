package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.adapter.Referee
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlackWinRenjuRuleTest {
    private var omokBoard = OmokBoard()
    private var referee = Referee()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    /*
        0 1 1 1 1
     */
    @Test
    fun `검은 돌 5개가 가로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint(6, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(7, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(8, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(9, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isWin(omokBoard, OmokPoint(5, 8), BlackStoneState)).isTrue
    }
    /*
            1
            1
            1
            1
            0
     */
    @Test
    fun `검은 돌 5개가 세로로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint(5, 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(5, 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isWin(omokBoard, OmokPoint(5, 8), BlackStoneState)).isTrue
    }
    /*
            0 0 0 0 1
            0 0 0 1 0
            0 0 1 0 0
            0 1 0 0 0
            0 0 0 0 0
     */
    @Test
    fun `검은 돌 5개가 대각으로 연속이면 승리한다`() {
        omokBoard = omokBoard.placeStone(OmokPoint(6, 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(7, 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(8, 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(9, 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isWin(omokBoard, OmokPoint(5, 8), BlackStoneState)).isTrue
    }

    /*
           0 1 1 1 1 1
     */
    @Test
    fun `검은 돌 6개가 연속이면 승리가 아니다`() {

        omokBoard = omokBoard.placeStone(OmokPoint(6, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(7, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(8, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(9, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(10, 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint(1, 1), WhiteStoneState)

        assertThat(referee.isWin(omokBoard, OmokPoint(5, 8), BlackStoneState)).isFalse
    }
}
