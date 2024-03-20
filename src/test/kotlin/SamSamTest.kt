import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.SamSamRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SamSamTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 33일 때`() {
        val board = Board()
        board.place(2, 3)
        board.place(2, 4)
        board.place(3, 2)
        board.place(4, 2)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접한 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val board = Board()
        board.place(2, 3)
        board.place(2, 4)
        board.place(4, 2)
        board.place(5, 2)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접하지 않은 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val board = Board()
        board.place(2, 3)
        board.place(2, 4)
        board.place(3, 2)
        board.place(5, 2)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 33`() {
        val board = Board()
        board.place(1, 2)
        board.place(3, 2)
        board.place(2, 1)
        board.place(2, 3)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 333`() {
        val board = Board()
        board.place(1, 1)
        board.place(3, 3)
        board.place(1, 2)
        board.place(3, 2)
        board.place(2, 1)
        board.place(2, 3)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `한칸씩 떨어진 십자형 33`() {
        val board = Board()
        board.place(1, 2)
        board.place(4, 2)
        board.place(2, 1)
        board.place(2, 4)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
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
        board.place(2, 5)
        board.place(2, 2)

        val actual = SamSamRule.check(board, StoneColor.BLACK)
        assertThat(actual).isFalse()
    }
}
