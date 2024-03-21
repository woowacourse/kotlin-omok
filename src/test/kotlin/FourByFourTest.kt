import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.FourByFourRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FourByFourTest {
    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44일 때`() {
        val board =
            Board()
                .place(3, 3)
                .place(4, 3)
                .place(5, 3)
                .place(6, 4)
                .place(6, 5)
                .place(6, 6)
                .place(6, 3)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44에 한 귀퉁이를 제일 마지막에 둘 때`() {
        val board =
            Board()
                .place(3, 3)
                .place(4, 3)
                .place(5, 3)
                .place(6, 3)
                .place(6, 4)
                .place(6, 5)
                .place(6, 6)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 세로 줄 사이 빈 곳이 없는 44이고 끝이 다른색 돌로 막혀 있을 때`() {
        val board =
            Board()
                .place(4, 3, StoneColor.BLACK)
                .place(5, 3, StoneColor.BLACK)
                .place(6, 3, StoneColor.BLACK)
                .place(7, 4, StoneColor.BLACK)
                .place(7, 5, StoneColor.BLACK)
                .place(7, 6, StoneColor.BLACK)
                .place(7, 7, StoneColor.WHITE)
                .place(7, 3, StoneColor.BLACK)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로 줄에 빈 칸 없고 세로 줄에 빈칸 있는 44`() {
        val board =
            Board()
                .place(3, 3)
                .place(4, 3)
                .place(5, 3)
                .place(6, 7)
                .place(6, 5)
                .place(6, 6)
                .place(6, 3)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `가로줄에 빈 칸 있고 세로 줄에 빈 칸 있는 44`() {
        val board =
            Board()
                .place(3, 3)
                .place(4, 3)
                .place(7, 3)
                .place(6, 7)
                .place(6, 5)
                .place(6, 6)
                .place(6, 3)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 44`() {
        val board =
            Board()
                .place(3, 4)
                .place(4, 4)
                .place(6, 4)
                .place(5, 3)
                .place(5, 5)
                .place(5, 6)
                .place(5, 4)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 대각선 444`() {
        val board =
            Board()
                .place(3, 4)
                .place(4, 4)
                .place(6, 4)
                .place(5, 3)
                .place(5, 5)
                .place(5, 6)
                .place(4, 3)
                .place(6, 5)
                .place(7, 6)
                .place(5, 4)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `십자형 43`() {
        val board =
            Board()
                .place(3, 4)
                .place(5, 4)
                .place(4, 3)
                .place(4, 5)
                .place(4, 6)
                .place(4, 4)

        val actual = FourByFourRule.check(board)
        assertThat(actual).isFalse()
    }
}
