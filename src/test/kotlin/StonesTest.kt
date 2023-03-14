import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class StonesTest {
    @Test
    fun `돌을 추가할 수 있다`() {
        val stones = Stones(Stone(1, 15))
        val nextStones = stones.putStone(Stone(3, 5))
        val expected = listOf(Stone(1, 15), Stone(3, 5))
        assertThat(nextStones.list).isEqualTo(expected)
    }

    @Test
    fun `마지막으로 놓은 돌의 위치를 알 수 있다`() {
        val stones = Stones()
        val nextStones = stones.putStone(Stone(1, 11))
        val expected = nextStones.getLatestStone()
        assertThat(expected).isEqualTo(Point(1, 11))
    }

    @Test
    fun `5개 이상의 돌이 연속되면 승리한다`() {
        assertAll(
            "5개 이상의 돌이 연속되면 승리한다",
            {
                val stones = Stones(Stone(1, 1), Stone(1, 2), Stone(1, 3), Stone(1, 4), Stone(1, 5))
                val actual = stones.isWin
                assertThat(actual).isTrue
            },
            {
                val stones = Stones(Stone(1, 1), Stone(1, 2), Stone(1, 3), Stone(1, 4), Stone(1, 5), Stone(1, 6))
                val actual = stones.isWin
                assertThat(actual).isTrue
            },
            {
                val stones = Stones(Stone(1, 1), Stone(2, 2), Stone(3, 3), Stone(4, 4), Stone(5, 5))
                val actual = stones.isWin
                assertThat(actual).isTrue
            }
        )
    }

    @Test
    fun `5개 이상의 돌이 연속되지 않으면 승리가 아니다`() {
        assertAll(
            "5개 이상의 돌이 연속되지 않으면 승리가 아니다",
            {
                val stones = Stones(Stone(1, 1), Stone(1, 2), Stone(1, 3), Stone(1, 4))
                val actual = stones.isWin
                assertThat(actual).isFalse
            },
            {
                val stones = Stones(Stone(1, 1), Stone(2, 2), Stone(4, 4), Stone(5, 5))
                val actual = stones.isWin
                assertThat(actual).isFalse
            }
        )
    }
}
