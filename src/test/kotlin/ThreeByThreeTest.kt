import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.ThreeByThreeRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ThreeByThreeTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 33일 때`() {
        val board =
            Board()
                .place(4, 5)
                .place(4, 6)
                .place(5, 4)
                .place(6, 4)
                .place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접한 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val board =
            Board()
                .place(4, 5)
                .place(4, 6)
                .place(6, 4)
                .place(7, 4)
                .place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `착수지점 바로 근접하지 않은 곳에 빈 칸이 있는 가로 세로 33일 때`() {
        val board =
            Board()
                .place(4, 5)
                .place(4, 6)
                .place(5, 4)
                .place(7, 4)
                .place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 33`() {
        val board =
            Board()
                .place(3, 4)
                .place(5, 4)
                .place(4, 3)
                .place(4, 5)
                .place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 333`() {
        val board =
            Board()
                .place(3, 3)
                .place(5, 5)
                .place(3, 4)
                .place(5, 4)
                .place(4, 3)
                .place(4, 5)
                .place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `한칸씩 떨어진 십자형 33`() {
        val board =
            Board()
                .place(3, 4)
                .place(6, 4)
                .place(4, 3)
                .place(4, 6)
                .place(4, 4)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 43`() {
        val board =
            Board()
                .place(1, 2)
                .place(3, 2)
                .place(2, 1)
                .place(2, 3)
                .place(2, 4)
                .place(2, 2)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `사이에 빈 칸이 있는 십자형 43`() {
        val board =
            Board()
                .place(3, 5)
                .place(4, 5)
                .place(7, 5)
                .place(5, 3)
                .place(5, 4)
                .place(5, 5)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `열림과 닫힘 `() {
        val board =
            Board()
                .place(4, 5, StoneColor.BLACK)
                .place(6, 5, StoneColor.BLACK)
                .place(5, 2, StoneColor.BLACK)
                .place(5, 3, StoneColor.BLACK)
                .place(2, 5, StoneColor.WHITE)
                .place(8, 5, StoneColor.WHITE)
                .place(5, 5, StoneColor.BLACK)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `한쪽 면 벽에 붙은 33은 금수가 아니다`() {
        val board =
            Board()
                .place(14, 5)
                .place(15, 5)
                .place(13, 3)
                .place(13, 4)
                .place(13, 5)
        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isFalse()
    }

    @Test
    fun `한 쪽 귀퉁이를 마지막으로 두는 십자형 33`() {
        val board =
            Board()
                .place(3, 4)
                .place(5, 4)
                .place(4, 4)
                .place(4, 3)
                .place(4, 5)

        val actual = ThreeByThreeRule.check(board)
        assertThat(actual).isTrue()
    }
}
