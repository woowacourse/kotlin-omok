package omok.domain

import omok.view.OutputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleFourTest {
    var omokBoard: OmokBoard = OmokBoard()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    @Test
    fun `직각 1, 0 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.WHITE)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.WHITE)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.WHITE)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.WHITE)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 1), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 1), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 1), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(1))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 2), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(1))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 15), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 14), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 11), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 10), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('C'), YCoordinate(13))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 12), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 12), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 12), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('F'), YCoordinate(12))
        assertThat(OmokRule(omokBoard).countOpenFours(point)).isEqualTo(2)
    }
}
