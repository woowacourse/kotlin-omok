import domain.judgement.RenjuRuleForbiddenCondition
import domain.stone.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuRuleForbiddenConditionTest {
    @Test
    fun `33금수 위치에 놓으면 true를 반환한다`() {
        val stones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(4, 13, Color.BLACK),
            Stone(4, 14, Color.BLACK),
            Stone(5, 12, Color.BLACK),
        )

        val forbiddenCondition = RenjuRuleForbiddenCondition()
        val actual = forbiddenCondition.isForbidden(stones.convertToBoard(), Stone(4, 12, Color.BLACK))
        assertThat(actual).isTrue
    }

    @Test
    fun `44금수 위치에 놓으면 true를 반환한다`() {
        val stones = listOf(
            Stone(6, 5, Color.BLACK),
            Stone(8, 6, Color.BLACK),
            Stone(8, 7, Color.BLACK),
            Stone(8, 8, Color.BLACK),
            Stone(10, 8, Color.BLACK),
            Stone(10, 9, Color.BLACK),
            Stone(11, 8, Color.BLACK),
        )

        val forbiddenCondition = RenjuRuleForbiddenCondition()
        val actual = forbiddenCondition.isForbidden(stones.convertToBoard(), Stone(9, 8, Color.BLACK))
        assertThat(actual).isTrue
    }

    @Test
    fun `장목에 놓으면 true를 반환한다`() {
        val stones = listOf(
            Stone(1, 1, Color.BLACK),
            Stone(1, 2, Color.BLACK),
            Stone(1, 3, Color.BLACK),
            Stone(1, 4, Color.BLACK),
            Stone(1, 6, Color.BLACK)
        )

        val forbiddenCondition = RenjuRuleForbiddenCondition()
        val actual = forbiddenCondition.isForbidden(stones.convertToBoard(), Stone(1, 5, Color.BLACK))
        assertThat(actual).isTrue
    }
}
