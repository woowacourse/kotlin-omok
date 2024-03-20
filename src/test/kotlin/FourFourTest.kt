import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule
import omok.model.rule.FourByFourRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FourFourTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44일 때`() {
        val board = Board()
        board.place(1, 1)
        board.place(2, 1)
        board.place(3, 1)
        board.place(4, 2)
        board.place(4, 3)
        board.place(4, 4)
        board.place(4, 1)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44이고 끝이 다른색 돌로 막혀 있을 때`() {
        val board = Board()
        board.place(2, 1, StoneColor.WHITE)
        board.place(2, 1, StoneColor.BLACK)
        board.place(3, 1, StoneColor.BLACK)
        board.place(4, 1, StoneColor.BLACK)
        board.place(5, 2, StoneColor.BLACK)
        board.place(5, 3, StoneColor.BLACK)
        board.place(5, 4, StoneColor.BLACK)
        board.place(5, 5, StoneColor.WHITE)

        board.place(5, 1, StoneColor.BLACK)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 줄에 빈 칸 없고 세로 줄에 빈칸 있는 44`() {
        val board = Board()
        board.place(1, 1)
        board.place(2, 1)
        board.place(3, 1)
        board.place(4, 5)
        board.place(4, 3)
        board.place(4, 4)
        board.place(4, 1)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로줄에 빈 칸 있고 세로 줄에 빈 칸 있는 44`() {
        val board = Board()
        board.place(1, 1)
        board.place(2, 1)
        board.place(5, 1)
        board.place(4, 5)
        board.place(4, 3)
        board.place(4, 4)
        board.place(4, 1)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 44`() {
        val board = Board()
        board.place(1, 2)
        board.place(2, 2)
        board.place(4, 2)
        board.place(3, 1)
        board.place(3, 3)
        board.place(3, 4)
        board.place(3, 2)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 444`() {
        val board = Board()
        board.place(1, 2)
        board.place(2, 2)
        board.place(4, 2)
        board.place(3, 1)
        board.place(3, 3)
        board.place(3, 4)
        board.place(2, 1)
        board.place(4, 3)
        board.place(5, 4)
        board.place(3, 2)

        val actual = FourByFourRule.check(board)
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

        val actual = FourByFourRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `돌 2개 빈 칸 돌 2개 빈 칸 돌 2개`() {
        val board = Board()
        board.place(1, 1)
        board.place(2, 1)

        board.place(5, 1)

        board.place(7, 1)
        board.place(8, 1)

        board.place(4, 1)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }
}
