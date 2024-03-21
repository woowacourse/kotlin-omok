import omok.model.Board
import omok.model.rule.ThreeByThreeRule
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

        val actual = ThreeByThreeRule.check(board)
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

        val actual = ThreeByThreeRule.check(board)
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

        val actual = ThreeByThreeRule.check(board)
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

        val actual = ThreeByThreeRule.check(board)
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

        val actual = ThreeByThreeRule.check(board)
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
}
