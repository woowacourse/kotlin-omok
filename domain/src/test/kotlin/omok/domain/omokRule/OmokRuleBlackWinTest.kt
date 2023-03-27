package omok.domain.omokRule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleBlackWinTest {
    private var board = MutableList(15) { MutableList(15) { 0 } }

    @BeforeEach
    fun setUp() {
        board = MutableList(15) { MutableList(15) { 0 } }
    }

    @Test
    fun `검은돌의 5개의 연속은 승리다(수평)`() {
        // given
        val x = 5
        val y = 1

        val nextPlace = RulePosition(x, y)

        // when
        board[1][1] = 1
        board[1][2] = 1
        board[1][3] = 1
        board[1][4] = 1

        // then
        assertThat(BlackWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `검은 돌의 5개가 연속이면 승리다(수직)`() {
        // given
        val x = 1
        val y = 5

        val nextPlace = RulePosition(x, y)

        // when
        board[1][1] = 1
        board[2][1] = 1
        board[3][1] = 1
        board[4][1] = 1

        // then
        assertThat(BlackWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `검은돌의 5개의 연속은 승리다(대각선)`() {
        // given
        val x = 5
        val y = 5

        val nextPlace = RulePosition(x, y)

        // when
        board[1][1] = 1
        board[2][2] = 1
        board[3][3] = 1
        board[4][4] = 1

        // then
        assertThat(BlackWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `검은돌의 5개의 연속은 승리다(대각선 반대)`() {
        // given
        val x = 1
        val y = 5

        val nextPlace = RulePosition(x, y)

        // when
        board[4][2] = 1
        board[3][3] = 1
        board[2][4] = 1
        board[1][5] = 1

        // then
        assertThat(BlackWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `검은 돌의 연속이 5개 이외면 승리가 아니다1`() {
        // given
        val x = 1
        val y = 4

        val nextPlace = RulePosition(x, y)

        // when
        board[1][1] = 1
        board[2][1] = 1
        board[3][1] = 1

        // then
        assertThat(BlackWinRule.validate(board, nextPlace)).isFalse
    }

    @Test
    fun `검은 돌의 연속이 5개 이외면 승리가 아니다2`() {
        // given
        val x = 1
        val y = 6

        val nextPlace = RulePosition(x, y)

        // when
        board[1][1] = 1
        board[2][1] = 1
        board[3][1] = 1
        board[4][1] = 1
        board[5][1] = 1

        // then
        assertThat(BlackWinRule.validate(board, nextPlace)).isFalse
    }
}
