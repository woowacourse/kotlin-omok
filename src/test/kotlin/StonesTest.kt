import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

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
}
