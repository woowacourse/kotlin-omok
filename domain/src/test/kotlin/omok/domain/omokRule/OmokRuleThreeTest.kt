
import omok.domain.omokRule.ThreeThreeRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleThreeTest {
    private var board = MutableList(15) { MutableList(15) { 0 } }

    @BeforeEach
    fun setUp() {
        board = MutableList(15) { MutableList(15) { 0 } }
    }

    @Test
    fun `열린 3이 두개 이상이면 둘 수 없다`() {
        // given
        val x = 3
        val y = 3

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        board[2][2] = 1
        board[3][2] = 1
        board[3][1] = 1

        // then
        assertThat(ThreeThreeRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `열린 3이 한개면 둘 수 있다`() {
        // given
        val x = 3
        val y = 3

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        board[2][2] = 1
        board[3][2] = 1

        // then
        assertThat(ThreeThreeRule.validate(board, nextPlace)).isFalse
    }

    @Test
    fun `34이면 둘 수 있다`() {
        // given
        val x = 3
        val y = 3

        val nextPlace = Pair(x, y)

        // when
        board[1][1] = 1
        board[2][2] = 1
        board[3][4] = 1
        board[3][2] = 1
        board[3][1] = 1

        // then
        assertThat(ThreeThreeRule.validate(board, nextPlace)).isFalse
    }

    @Test
    fun `33중 막힌 3이 하나라도 있으면 33이 아니다`() {
        // given
        val x = 6
        val y = 5

        val nextPlace = Pair(x, y)

        // when
        board[5][3] = 2
        board[5][5] = 1
        board[5][7] = 1
        board[5][9] = 2

        board[6][5] = 1
        board[7][5] = 1

        // then
        assertThat(ThreeThreeRule.validate(board, nextPlace)).isFalse
    }
}
