package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.OmokRule
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import omok.view.OutputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleWinTest {
    var omokBoard: OmokBoard = OmokBoard()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    @Test
    fun `검은 돌의 연속이 5개 이상이면 검은 돌이 이긴다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint('H', 6)
        assertThat(OmokRule(omokBoard, BlackStoneState).validateBlackWin(point)).isTrue
    }

    @Test
    fun `검은 돌의 연속이 4개 이하면 이기지 않는다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint('H', 6)
        assertThat(OmokRule(omokBoard, BlackStoneState).validateBlackWin(point)).isFalse
    }

    @Test
    fun `흰 돌의 연속이 5개 이상이면 흰 돌이 이긴다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint('G', 6)
        assertThat(OmokRule(omokBoard, WhiteStoneState).validateWhiteWin(point)).isTrue
    }

    @Test
    fun `흰 돌의 연속이 4개 이하면 이기지 않는다`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), BlackStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint('F', 6)
        assertThat(OmokRule(omokBoard, WhiteStoneState).validateWhiteWin(point)).isFalse
    }
}
