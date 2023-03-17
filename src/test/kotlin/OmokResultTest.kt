import domain.OmokResult
import domain.stone.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokResultTest {
    @Test
    fun `돌이 5개 이상 이어지면 FIVE_STONES_WINNING_CONDITION을 반환한다`() {
        val stones = listOf(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.WHITE)
        )
        val actual = OmokResult.valueOf(stones, Stone(1, 5, Color.WHITE))
        assertThat(actual).isEqualTo(OmokResult.FIVE_STONE_WINNING)
    }

    @Test
    fun `돌이 5개 이상 이어지지 않았으면 RUNNING을 반환한다`() {
        val stones = listOf(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.WHITE)
        )
        val actual = OmokResult.valueOf(stones, Stone(1, 6, Color.WHITE))
        assertThat(actual).isEqualTo(OmokResult.RUNNING)
    }

    @Test
    fun `33에 해당하는 수를 놓으면 FORBIDDEN을 반환한다`() {
        val stones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(4, 13, Color.BLACK),
            Stone(4, 14, Color.BLACK),
            Stone(5, 12, Color.BLACK),
        )
        val actual = OmokResult.valueOf(stones, Stone(4, 12, Color.BLACK))
        assertThat(actual).isEqualTo(OmokResult.FORBIDDEN)
    }

    @Test
    fun `44에 해당하는 수를 놓으면 FORBIDDEN을 반환한다`() {
        val stones = listOf(
            Stone(6, 5, Color.BLACK),
            Stone(8, 6, Color.BLACK),
            Stone(8, 7, Color.BLACK),
            Stone(8, 8, Color.BLACK),
            Stone(10, 8, Color.BLACK),
            Stone(10, 9, Color.BLACK),
            Stone(11, 8, Color.BLACK),
        )
        val actual = OmokResult.valueOf(stones, Stone(9, 8, Color.BLACK))
        assertThat(actual).isEqualTo(OmokResult.FORBIDDEN)
    }
}
