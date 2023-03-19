
import omok.domain.omokRule.FourFourRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokRuleFourTest {
    private var board = MutableList(15) { MutableList(15) { 0 } }

    @BeforeEach
    fun setUp() {
        board = MutableList(15) { MutableList(15) { 0 } }
    }

    @Test
    fun `열린 4이 두개 이상이면 둘 수 없다`() {
        // given
        val x = 1
        val y = 1

        val nextPlace = Pair(x, y)

        // when
        board[1][2] = 1
        board[1][3] = 1
        board[1][4] = 1

        board[2][1] = 1
        board[3][1] = 1
        board[4][1] = 1

        // then
        assertThat(FourFourRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `한 쪽이 막힌 4가 한개가 있어도 둘 수 없다 `() {
        // given
        val x = 1
        val y = 1

        val nextPlace = Pair(x, y)

        // when
        board[1][2] = 1
        board[1][3] = 1
        board[1][4] = 1

        board[2][1] = 1
        board[3][1] = 1
        board[4][1] = 1
        board[5][1] = 2

        // then
        assertThat(FourFourRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `한 쪽이 막힌 4가 두개가 있어도 둘 수 없다 `() {
        // given
        val x = 1
        val y = 1

        val nextPlace = Pair(x, y)

        // when
        board[1][2] = 1
        board[1][3] = 1
        board[1][4] = 1
        board[1][5] = 2

        board[2][1] = 1
        board[3][1] = 1
        board[4][1] = 1
        board[5][1] = 2

        // then
        assertThat(FourFourRule.validate(board, nextPlace)).isTrue
    }

    @Test
    fun `5개 이상의 연속은 잡지 않는다`() {
        // given
        val x = 1
        val y = 1

        val nextPlace = Pair(x, y)

        // when
        board[1][2] = 1
        board[1][3] = 1
        board[1][4] = 1
        board[1][5] = 1

        board[2][1] = 1
        board[3][1] = 1
        board[4][1] = 1
        board[5][1] = 1

        // then
        assertThat(FourFourRule.validate(board, nextPlace)).isFalse
    }
}
