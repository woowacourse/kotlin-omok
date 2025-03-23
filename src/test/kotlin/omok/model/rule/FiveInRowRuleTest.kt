package omok.model.rule

import omok.fixture.generateTestBoardFixture
import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.count.FiveInRowRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FiveInRowRuleTest {
    private val verticalFiveInRowBoard: Board =
        generateTestBoardFixture(
            listOf(
                Point(1, 1),
                Point(2, 1),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1),
            ),
            PointState.BLACK,
        )

    private val horizontalFiveInRowBoard: Board =
        generateTestBoardFixture(
            listOf(
                Point(1, 1),
                Point(1, 2),
                Point(1, 3),
                Point(1, 4),
                Point(1, 5),
            ),
            PointState.BLACK,
        )

    private val diagonalFiveInRowBoard: Board =
        generateTestBoardFixture(
            listOf(
                Point(1, 1),
                Point(2, 2),
                Point(3, 3),
                Point(4, 4),
                Point(5, 5),
            ),
            PointState.BLACK,
        )

    private val antiDiagonalFiveInRowBoard: Board =
        generateTestBoardFixture(
            listOf(
                Point(1, 5),
                Point(2, 4),
                Point(3, 3),
                Point(4, 2),
                Point(5, 1),
            ),
            PointState.BLACK,
        )

    @Test
    fun `세로로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 1)
        assertTrue(FiveInRowRule().calculate(verticalFiveInRowBoard, point))
    }

    @Test
    fun `가로로 오목인 경우 true를 반환한다`() {
        val point = Point(1, 5)
        assertTrue(FiveInRowRule().calculate(horizontalFiveInRowBoard, point))
    }

    @Test
    fun `대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 5)
        assertTrue(FiveInRowRule().calculate(diagonalFiveInRowBoard, point))
    }

    @Test
    fun `반대 대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 1)
        assertTrue(FiveInRowRule().calculate(antiDiagonalFiveInRowBoard, point))
    }

    @Test
    fun `오목이 없는 경우 false를 반환한다`() {
        val point = Point(14, 1)
        assertFalse(FiveInRowRule().calculate(horizontalFiveInRowBoard, point))
    }
}
