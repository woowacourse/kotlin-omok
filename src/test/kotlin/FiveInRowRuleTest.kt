import omok.model.board.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FiveInRowRuleTest {
    @Test
    fun `가로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val stones =
            setOf(
                Stone(Point(1, 1), StoneColor.BLACK),
                Stone(Point(2, 1), StoneColor.BLACK),
                Stone(Point(3, 1), StoneColor.BLACK),
                Stone(Point(4, 1), StoneColor.BLACK),
                Stone(Point(5, 1), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `왼쪽 위에서 오른쪽 아래 대각선으로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val stones =
            setOf(
                Stone(Point(1, 1), StoneColor.BLACK),
                Stone(Point(2, 2), StoneColor.BLACK),
                Stone(Point(3, 3), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
                Stone(Point(5, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `세로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val stones =
            setOf(
                Stone(Point(1, 1), StoneColor.BLACK),
                Stone(Point(1, 2), StoneColor.BLACK),
                Stone(Point(1, 3), StoneColor.BLACK),
                Stone(Point(1, 4), StoneColor.BLACK),
                Stone(Point(1, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `오른쪽 위에서 왼쪽 아래 대각선으로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val stones =
            setOf(
                Stone(Point(5, 1), StoneColor.BLACK),
                Stone(Point(4, 2), StoneColor.BLACK),
                Stone(Point(3, 3), StoneColor.BLACK),
                Stone(Point(2, 4), StoneColor.BLACK),
                Stone(Point(1, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `5개가 지렁이꼴로 연달아 나타나지만 오목은 아닐 때`() {
        val stones =
            setOf(
                Stone(Point(1, 1), StoneColor.BLACK),
                Stone(Point(2, 2), StoneColor.BLACK),
                Stone(Point(1, 3), StoneColor.BLACK),
                Stone(Point(2, 4), StoneColor.BLACK),
                Stone(Point(1, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isFalse()
    }
}
