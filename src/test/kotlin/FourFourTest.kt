import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.FourByFourRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FourFourTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44일 때`() {
        val board = Board()
        board.place(3, 3)
        board.place(4, 3)
        board.place(5, 3)
        board.place(6, 4)
        board.place(6, 5)
        board.place(6, 6)
        board.place(6, 3)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44이고 끝이 다른색 돌로 막혀 있을 때`() {
        val board = Board()
        board.place(4, 3, StoneColor.BLACK)
        board.place(5, 3, StoneColor.BLACK)
        board.place(6, 3, StoneColor.BLACK)
        board.place(7, 4, StoneColor.BLACK)
        board.place(7, 5, StoneColor.BLACK)
        board.place(7, 6, StoneColor.BLACK)
        board.place(7, 7, StoneColor.WHITE)

        board.place(7, 3, StoneColor.BLACK)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 줄에 빈 칸 없고 세로 줄에 빈칸 있는 44`() {
        val board = Board()
        board.place(3, 3)
        board.place(4, 3)
        board.place(5, 3)
        board.place(6, 7)
        board.place(6, 5)
        board.place(6, 6)
        board.place(6, 3)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로줄에 빈 칸 있고 세로 줄에 빈 칸 있는 44`() {
        val board = Board()
        board.place(3, 3)
        board.place(4, 3)
        board.place(7, 3)
        board.place(6, 7)
        board.place(6, 5)
        board.place(6, 6)
        board.place(6, 3)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 44`() {
        val board = Board()
        board.place(3, 4)
        board.place(4, 4)
        board.place(6, 4)
        board.place(5, 3)
        board.place(5, 5)
        board.place(5, 6)
        board.place(5, 4)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 444`() {
        val board = Board()
        board.place(3, 4)
        board.place(4, 4)
        board.place(6, 4)
        board.place(5, 3)
        board.place(5, 5)
        board.place(5, 6)
        board.place(4, 3)
        board.place(6, 5)
        board.place(7, 6)
        board.place(5, 4)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 43`() {
        val board = Board()
        board.place(3, 4)
        board.place(5, 4)
        board.place(4, 3)
        board.place(4, 5)
        board.place(4, 6)
        board.place(4, 4)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isFalse()
    }
}
