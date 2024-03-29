import omok.model.Board
import omok.model.rule.FiveInRowRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FiveInRowRuleTest {
    @Test
    fun `가로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(2, 1)
                .place(3, 1)
                .place(4, 1)
                .place(5, 1)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `왼쪽 위에서 오른쪽 아래 대각선으로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(2, 2)
                .place(3, 3)
                .place(4, 4)
                .place(5, 5)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `세로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(1, 2)
                .place(1, 3)
                .place(1, 4)
                .place(1, 5)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `오른쪽 위에서 왼쪽 아래 대각선으로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board =
            Board()
                .place(5, 1)
                .place(4, 2)
                .place(3, 3)
                .place(2, 4)
                .place(1, 5)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `5개가 지렁이꼴로 연달아 나타나지만 오목은 아닐 때`() {
        val board =
            Board()
                .place(1, 1)
                .place(2, 2)
                .place(1, 3)
                .place(2, 4)
                .place(1, 5)
        val actual = FiveInRowRule.check(board)
        assertThat(actual).isFalse()
    }
}
