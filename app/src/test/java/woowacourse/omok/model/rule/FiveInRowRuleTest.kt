package omok.model.rule

import omok.fixture.antiDiagonalFiveInRowBoard
import omok.fixture.diagonalFiveInRowBoard
import omok.fixture.horizontalFiveInRowBoard
import omok.fixture.verticalFiveInRowBoard
import omok.model.rule.count.FiveInRowRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.Point

class FiveInRowRuleTest {
    val rule = FiveInRowRule()

    @Test
    fun `세로로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 1).apply { changeState(StoneColor.WHITE) }

        assertTrue(rule.calculate(verticalFiveInRowBoard, point))
    }

    @Test
    fun `가로로 오목인 경우 true를 반환한다`() {
        val point = Point(1, 5).apply { changeState(StoneColor.WHITE) }
        assertTrue(rule.calculate(horizontalFiveInRowBoard, point))
    }

    @Test
    fun `대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 5).apply { changeState(StoneColor.BLACK) }
        assertTrue(rule.calculate(diagonalFiveInRowBoard, point))
    }

    @Test
    fun `반대 대각선으로 오목인 경우 true를 반환한다`() {
        val point = Point(5, 1).apply { changeState(StoneColor.BLACK) }
        assertTrue(rule.calculate(antiDiagonalFiveInRowBoard, point))
    }

    @Test
    fun `오목이 없는 경우 false를 반환한다`() {
        val point = Point(14, 1).apply { changeState(StoneColor.WHITE) }
        assertFalse(rule.calculate(horizontalFiveInRowBoard, point))
    }
}
