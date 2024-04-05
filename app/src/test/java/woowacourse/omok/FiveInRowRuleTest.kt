import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule
import omok.model.turn.BlackTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FiveInRowRuleTest {
    @Test
    fun `흑 턴일때 돌을 놓으면 흑돌이다`() {
        val board = Board()
        val point = Point(1, 1)
        val stone = Stone(point, StoneColor.BLACK)
        val nextBoard = BlackTurn(board).nextTurn(point).board
        val actual = nextBoard.previousStone()
        assertThat(actual).isEqualTo(stone)
    }

    @Test
    fun `가로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board =
            Board()
                .place(1, 1)
                .place(2, 1)
                .place(3, 1)
                .place(4, 1)
        val stone = stone(5, 1)
        val actual = FiveInRowRule.check(stone, board)
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
        val stone = stone(5, 5)
        val actual = FiveInRowRule.check(stone, board)
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
        val stone = stone(1, 5)
        val actual = FiveInRowRule.check(stone, board)
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
        val stone = stone(1, 5)
        val actual = FiveInRowRule.check(stone, board)
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
        val stone = stone(1, 5)
        val actual = FiveInRowRule.check(stone, board)
        assertThat(actual).isFalse()
    }
}
