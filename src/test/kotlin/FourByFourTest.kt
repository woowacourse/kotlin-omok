import omok.model.board.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FourByFourRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FourByFourTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44일 때`() {
        val stones =
            setOf(
                Stone(Point(3, 3), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(6, 4), StoneColor.BLACK),
                Stone(Point(6, 5), StoneColor.BLACK),
                Stone(Point(6, 6), StoneColor.BLACK),
                Stone(Point(6, 3), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44이고 끝이 다른색 돌로 막혀 있을 때`() {
        val stones =
            setOf(
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(6, 3), StoneColor.BLACK),
                Stone(Point(7, 4), StoneColor.BLACK),
                Stone(Point(7, 5), StoneColor.BLACK),
                Stone(Point(7, 6), StoneColor.BLACK),
                Stone(Point(7, 7), StoneColor.WHITE),
                Stone(Point(7, 3), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 줄에 빈 칸 없고 세로 줄에 빈칸 있는 44`() {
        val stones =
            setOf(
                Stone(Point(3, 3), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(6, 7), StoneColor.BLACK),
                Stone(Point(6, 5), StoneColor.BLACK),
                Stone(Point(6, 6), StoneColor.BLACK),
                Stone(Point(6, 3), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로줄에 빈 칸 있고 세로 줄에 빈 칸 있는 44`() {
        val stones =
            setOf(
                Stone(Point(3, 3), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(7, 3), StoneColor.BLACK),
                Stone(Point(6, 7), StoneColor.BLACK),
                Stone(Point(6, 5), StoneColor.BLACK),
                Stone(Point(6, 6), StoneColor.BLACK),
                Stone(Point(6, 3), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 44`() {
        val stones =
            setOf(
                Stone(Point(3, 4), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
                Stone(Point(6, 4), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(5, 5), StoneColor.BLACK),
                Stone(Point(5, 6), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 444`() {
        val stones =
            setOf(
                Stone(Point(3, 4), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
                Stone(Point(6, 4), StoneColor.BLACK),
                Stone(Point(5, 3), StoneColor.BLACK),
                Stone(Point(5, 5), StoneColor.BLACK),
                Stone(Point(5, 6), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(6, 5), StoneColor.BLACK),
                Stone(Point(7, 6), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 43`() {
        val stones =
            setOf(
                Stone(Point(3, 4), StoneColor.BLACK),
                Stone(Point(5, 4), StoneColor.BLACK),
                Stone(Point(4, 3), StoneColor.BLACK),
                Stone(Point(4, 5), StoneColor.BLACK),
                Stone(Point(4, 6), StoneColor.BLACK),
                Stone(Point(4, 4), StoneColor.BLACK),
            )
        val board = Board(stones)
        val actual = FourByFourRule.check(board)
        assertThat(actual).isFalse()
    }
}
