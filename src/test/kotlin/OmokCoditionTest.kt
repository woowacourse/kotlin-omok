import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokCoditionTest {
    @Test
    fun `돌이 5개 이상 이어지면 FIVE_STONES_WINNING_CONDITION을 반환한다`() {
        val stones = listOf(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.WHITE),
            Stone(1, 5, Color.WHITE)
        )
        val actual = OmokCondition.valueOf(stones, Color.WHITE)
        assertThat(actual).isEqualTo(OmokCondition.FIVE_STONES_WINNING_CONDITION)
    }

    @Test
    fun `돌이 5개 이상 이어지지 않았으면 RUNNING을 반환한다`() {
        val stones = listOf(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.WHITE),
            Stone(1, 6, Color.WHITE)
        )
        val actual = OmokCondition.valueOf(stones, Color.WHITE)
        assertThat(actual).isEqualTo(OmokCondition.RUNNING)
    }
}
