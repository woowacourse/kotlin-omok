import omok.model.Board
import omok.model.rule.OverSixInRowRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OverSixInRowRuleTest {
    @Test
    fun `가로로 같은 색 돌이 6개 연속으로 있을 경우 육목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(2, 1)
                .place(3, 1)
                .place(4, 1)
                .place(5, 1)
                .place(6, 1)
        val actual = OverSixInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `왼쪽 위에서 오른쪽 아래 대각선으로 같은 색 돌이 6개 연속으로 있을 경우 육목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(2, 2)
                .place(3, 3)
                .place(4, 4)
                .place(5, 5)
                .place(6, 6)
        val actual = OverSixInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `세로로 같은 색 돌이 6개 연속으로 있을 경우 육목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(1, 2)
                .place(1, 3)
                .place(1, 4)
                .place(1, 5)
                .place(1, 6)
        val actual = OverSixInRowRule.check(board)
        assertThat(actual).isTrue()
    }

    @Test
    fun `오른쪽 위에서 왼쪽 아래 대각선으로 같은 색 돌이 6개 연속으로 있을 경우 육목이다`() {
        val board =
            Board()
                .place(6, 1)
                .place(5, 2)
                .place(4, 3)
                .place(3, 4)
                .place(2, 5)
                .place(1, 6)
        val actual = OverSixInRowRule.check(board)
        assertThat(actual).isTrue()
    }
}
