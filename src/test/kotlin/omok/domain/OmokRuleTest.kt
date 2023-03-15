package omok.domain

import omok.view.OutputView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleTest {
    var omokBoard: OmokBoard = OmokBoard()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    @Test
    fun `직각 1, 0 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 2), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 2), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 10), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 10), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 1 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 0, 1 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 10), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 10), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 9), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 8), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 2), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 2), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, -1 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 1 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(1)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 2), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 0 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 2), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 3), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 4), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외4_`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 7), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 10), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 9), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 10), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 3), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }

    @Test
    fun `대각 1, 1 룰 적용 예외10`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 5), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 4), StoneState.BLACK)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 7), StoneState.WHITE)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard).findSamSam(point)).isEqualTo(0)
    }
}
