package omok.domain.omokRule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleWhiteWinTest {
    private var board = MutableList(15) { MutableList(15) { 0 } }

    @BeforeEach
    fun setUp() {
        board = MutableList(15) { MutableList(15) { 0 } }
    }

    @Test
    fun `흰 돌의 5개의 연속은 승리다(수평)`() {
        // given
        val x = 5
        val y = 1

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 2
        board[1][2] = 2
        board[1][3] = 2
        board[1][4] = 2

        // then
        assertThat(WhiteWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `흰 돌의 5개가 연속이면 승리다(수직)`() {
        // given
        val x = 1
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 2
        board[2][1] = 2
        board[3][1] = 2
        board[4][1] = 2

        // then
        assertThat(WhiteWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `흰 돌의 5개의 연속은 승리다(대각선)`() {
        // given
        val x = 5
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 2
        board[2][2] = 2
        board[3][3] = 2
        board[4][4] = 2

        // then
        assertThat(WhiteWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `흰 돌의 5개의 연속은 승리다(대각선 반대)`() {
        // given
        val x = 1
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[4][2] = 2
        board[3][3] = 2
        board[2][4] = 2
        board[1][5] = 2

        // then
        assertThat(WhiteWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `흰 돌의 연속이 5개 초과면 승리다`() {
        // given
        val x = 1
        val y = 6

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 2
        board[2][1] = 2
        board[3][1] = 2
        board[4][1] = 2
        board[5][1] = 2

        // then
        assertThat(WhiteWinRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `흰 돌의 연속이 5개 미만 승리가 아니다`() {
        // given
        val x = 1
        val y = 4

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 2
        board[2][1] = 2
        board[3][1] = 2

        // then
        assertThat(WhiteWinRule.validate(board, nextPlace)).isFalse
    }
}
