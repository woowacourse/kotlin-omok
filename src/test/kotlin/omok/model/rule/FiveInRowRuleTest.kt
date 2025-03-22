package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.count.FiveInRowRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FiveInRowRuleTest {
    private val verticalFiveInRowBoard: Board =
        Board().apply {
            placeStone(Point(1, 1), PointState.WHITE)
            placeStone(Point(2, 1), PointState.WHITE)
            placeStone(Point(3, 1), PointState.WHITE)
            placeStone(Point(4, 1), PointState.WHITE)
            placeStone(Point(5, 1), PointState.WHITE)
        }

    private val horizontalFiveInRowBoard: Board =
        Board().apply {
            placeStone(Point(1, 1), PointState.WHITE)
            placeStone(Point(1, 2), PointState.WHITE)
            placeStone(Point(1, 3), PointState.WHITE)
            placeStone(Point(1, 4), PointState.WHITE)
            placeStone(Point(1, 5), PointState.WHITE)
        }

    private val diagonalFiveInRowBoard: Board =
        Board().apply {
            placeStone(Point(1, 1), PointState.WHITE)
            placeStone(Point(2, 2), PointState.WHITE)
            placeStone(Point(3, 3), PointState.WHITE)
            placeStone(Point(4, 4), PointState.WHITE)
            placeStone(Point(5, 5), PointState.WHITE)
        }

    private val antiDiagonalFiveInRowBoard: Board =
        Board().apply {
            placeStone(Point(1, 5), PointState.WHITE)
            placeStone(Point(2, 4), PointState.WHITE)
            placeStone(Point(3, 3), PointState.WHITE)
            placeStone(Point(4, 2), PointState.WHITE)
            placeStone(Point(5, 1), PointState.WHITE)
        }

    @Test
    fun `세로로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 1)
        assertTrue(FiveInRowRule.calculate(verticalFiveInRowBoard, point))
    }

    @Test
    fun `가로로 오목인 경우 true를 반환한다`() {
        val point = Point(1, 5)
        assertTrue(FiveInRowRule.calculate(horizontalFiveInRowBoard, point))
    }

    @Test
    fun `대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 5)
        assertTrue(FiveInRowRule.calculate(diagonalFiveInRowBoard, point))
    }

    @Test
    fun `반대 대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 1)
        assertTrue(FiveInRowRule.calculate(antiDiagonalFiveInRowBoard, point))
    }

    @Test
    fun `오목이 없는 경우 false를 반환한다`() {
        val point = Point(14, 1)
        assertFalse(FiveInRowRule.calculate(horizontalFiveInRowBoard, point))
    }
}
