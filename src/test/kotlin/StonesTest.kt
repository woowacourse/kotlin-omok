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
}