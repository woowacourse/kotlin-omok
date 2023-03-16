package omok.domain.omokRule

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.OmokRule
import omok.domain.XCoordinate
import omok.domain.YCoordinate
import omok.domain.state.BlackStoneState
import omok.domain.state.WhiteStoneState
import omok.view.OutputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleThreeTest {
    var omokBoard: OmokBoard = OmokBoard()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    @Test
    fun `직각 1, 0 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 2), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 2), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 10), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 10), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 1 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 10), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 10), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 2), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 2), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 1 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 2), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 0 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 2), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 10), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 10), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenThrees(point)).isEqualTo(0)
    }
}
