import domain.judgement.FiveStoneWinningCondition
import domain.stone.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FiveStoneWinningConditionTest {
    @Test
    fun `돌이 5개 이상 이어지면 true를 반환한다`() {
        val stones = listOf(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.WHITE)
        )

        val fiveStoneWinningCondition = FiveStoneWinningCondition()
        val actual = fiveStoneWinningCondition.isWin(stones.convertToBoard(), Stone(1, 5, Color.WHITE))
        assertThat(actual).isTrue
    }
}
