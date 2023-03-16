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

class OmokRuleFourTest {
    var omokBoard: OmokBoard = OmokBoard()

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    @Test
    fun `직각 1, 0 룰 적용1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외3`() {
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외4`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 6), WhiteStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 6), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외5`() {
        omokBoard = omokBoard.placeStone(OmokPoint('I', 1), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 1), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 1), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('L', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(1))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외6`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 2), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(1))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외7`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 3), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(6))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외8`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 15), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 14), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('C'), YCoordinate(13))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(0)
    }

    @Test
    fun `직각 1, 0 룰 적용 예외9`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('F'), YCoordinate(12))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `4-4 test onlyGaro1`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('F'), YCoordinate(12))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `4-4 test onlyGaro1-banrya`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('D', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('I', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 12), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('F'), YCoordinate(12))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `4-4 test onlyGaro2`() {
        omokBoard = omokBoard.placeStone(OmokPoint('A', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('B', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('C'), YCoordinate(12))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(1)
    }

    @Test
    fun `4-4 test sero j10 renju-Rule `() {
        omokBoard = omokBoard.placeStone(OmokPoint('J', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('J'), YCoordinate(10))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `4-4 test jikgak`() {
        omokBoard = omokBoard.placeStone(OmokPoint('E', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('H'), YCoordinate(5))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `4-4 test garo-daegak`() {
        omokBoard = omokBoard.placeStone(OmokPoint('H', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('K', 8), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('J', 9), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('H', 7), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('I'), YCoordinate(8))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }

    @Test
    fun `4-4 test sero-daegak`() {
        omokBoard = omokBoard.placeStone(OmokPoint('C', 12), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 11), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('C', 10), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('G', 4), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('F', 5), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('E', 6), BlackStoneState)
        omokBoard = omokBoard.placeStone(OmokPoint('A', 1), WhiteStoneState)

        OutputView().outputBoard(omokBoard)
        val point = OmokPoint(XCoordinate('C'), YCoordinate(8))
        assertThat(OmokRule(omokBoard, BlackStoneState).countOpenFours(point)).isEqualTo(2)
    }
}
