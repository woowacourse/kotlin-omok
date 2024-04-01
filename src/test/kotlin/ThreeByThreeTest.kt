import omok.model.board.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.ThreeByThreeRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ThreeByThreeTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 33일 때`() {
        val stones =
            setOf(
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(4, 6), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
                Stone(Point(6, 4), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접한 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val stones =
            setOf(
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(4, 6), StoneColor.BLACK),
                Stone(Point(6, 4), StoneColor.BLACK),
                Stone(Point(7, 4), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접하지 않은 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val stones =
            setOf(
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(4, 6), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
                Stone(Point(7, 4), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 33`() {
        val stones =
            setOf(
                Stone(Point(3, 4), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 333`() {
        val stones =
            setOf(
                Stone(Point(3, 3), StoneColor.BLACK),
                Stone(Point(5, 5), StoneColor.BLACK),
                Stone(Point(3, 4), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `한칸씩 떨어진 십자형 33`() {
        val stones =
            setOf(
                Stone(Point(3, 4), StoneColor.BLACK),
                Stone(Point(6, 4), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(4, 6), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 43`() {
        val stones =
            setOf(
                Stone(Point(1, 2), StoneColor.BLACK),
                Stone(Point(3, 2), StoneColor.BLACK),
                Stone(Point(2, 1), StoneColor.BLACK),
                Stone(Point(2, 3), StoneColor.BLACK),
                Stone(Point(2, 4), StoneColor.BLACK),
                Stone(Point(2, 2), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `사이에 빈 칸이 있는 십자형 43`() {
        val stones =
            setOf(
                Stone(Point(3, 5), StoneColor.BLACK),
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(7, 5), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
                Stone(Point(5, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `열림과 닫힘 `() {
        val stones =
            setOf(
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(6, 5), StoneColor.BLACK),
                Stone(Point(5, 2), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(2, 5), StoneColor.WHITE),
                Stone(Point(8, 5), StoneColor.WHITE),
                Stone(Point(5, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `한쪽 면 벽에 붙은 33은 금수가 아니다`() {
        val stones =
            setOf(
                Stone(Point(14, 5), StoneColor.BLACK),
                Stone(Point(15, 5), StoneColor.BLACK),
                Stone(Point(13, 3), StoneColor.BLACK),
                Stone(Point(13, 4), StoneColor.BLACK),
                Stone(Point(13, 5), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }
}
