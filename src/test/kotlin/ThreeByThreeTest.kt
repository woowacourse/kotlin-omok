import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.ThreeByThreeRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ThreeByThreeTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 33일 때`() {
        val board = Board()
        board.place(4, 5)
        board.place(4, 6)
        board.place(5, 4)
        board.place(6, 4)
        board.place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접한 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val board = Board()
        board.place(4, 5)
        board.place(4, 6)
        board.place(6, 4)
        board.place(7, 4)
        board.place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접하지 않은 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val board = Board()
        board.place(4, 5)
        board.place(4, 6)
        board.place(5, 4)
        board.place(7, 4)

        board.place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 33`() {
        val board = Board()
        board.place(3, 4)
        board.place(5, 4)
        board.place(4, 3)
        board.place(4, 5)
        board.place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 333`() {
        val board = Board()
        board.place(3, 3)
        board.place(5, 5)
        board.place(3, 4)
        board.place(5, 4)
        board.place(4, 3)
        board.place(4, 5)
        board.place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `한칸씩 떨어진 십자형 33`() {
        val board = Board()
        board.place(3, 4)
        board.place(6, 4)
        board.place(4, 3)
        board.place(4, 6)

        board.place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 43`() {
        val board = Board()
        board.place(1, 2)
        board.place(3, 2)
        board.place(2, 1)
        board.place(2, 3)
        board.place(2, 4)
        board.place(2, 2)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `사이에 빈 칸이 있는 십자형 43`() {
        val board = Board()
        board.place(3, 5)
        board.place(4, 5)
        board.place(7, 5)
        board.place(5, 3)
        board.place(5, 4)

        board.place(5, 5)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `열림과 닫힘 `() {
        val board = Board()
        board.place(4, 5, StoneColor.BLACK)
        board.place(6, 5, StoneColor.BLACK)
        board.place(5, 2, StoneColor.BLACK)
        board.place(5, 3, StoneColor.BLACK)

        board.place(2, 5, StoneColor.WHITE)
        board.place(8, 5, StoneColor.WHITE)

        board.place(5, 5, StoneColor.BLACK)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `한쪽 면 벽에 붙은 33은 금수가 아니다`() {
        val board = Board()
        board.place(14, 5)
        board.place(15, 5)
        board.place(13, 3)
        board.place(13, 4)

        board.place(13, 5)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }
}
