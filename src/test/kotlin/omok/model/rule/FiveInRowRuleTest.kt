package omok.model.rule

import omok.fixture.antiDiagonalFiveInRowBoard
import omok.fixture.diagonalFiveInRowBoard
import omok.fixture.horizontalFiveInRowBoard
import omok.fixture.verticalFiveInRowBoard
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.board.Position
import omok.model.rule.count.FiveInRowRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FiveInRowRuleTest {
    @Test
    fun `세로로 오목인 경우 true를 반환한다`() {
        val point = Point(Position(5, 1)).apply { changeState(PointState.WHITE) }
        assertTrue(FiveInRowRule.calculate(verticalFiveInRowBoard, point))
    }

    @Test
    fun `가로로 오목인 경우 true를 반환한다`() {
        val point = Point(Position(1, 5)).apply { changeState(PointState.WHITE) }
        assertTrue(FiveInRowRule.calculate(horizontalFiveInRowBoard, point))
    }

    @Test
    fun `대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(Position(5, 5)).apply { changeState(PointState.BLACK) }
        assertTrue(FiveInRowRule.calculate(diagonalFiveInRowBoard, point))
    }

    @Test
    fun `반대 대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(Position(5, 1)).apply { changeState(PointState.BLACK) }
        assertTrue(FiveInRowRule.calculate(antiDiagonalFiveInRowBoard, point))
    }

    @Test
    fun `오목이 없는 경우 false를 반환한다`() {
        val point = Point(Position(14, 1)).apply { changeState(PointState.WHITE) }
        assertFalse(FiveInRowRule.calculate(horizontalFiveInRowBoard, point))
    }
}
