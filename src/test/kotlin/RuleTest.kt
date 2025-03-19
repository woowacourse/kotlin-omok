import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RuleTest {
    @Test
    fun `가로 돌이 5개 이상인지 판단할 수 있다`() {
        val rule = Rule()
        val stone = STONE_1A_BLACK
        val stones = listOf(STONE_1C_BLACK, STONE_1B_BLACK, STONE_1D_BLACK, STONE_1E_BLACK)
        assertTrue(rule.isHorizontal(stone, stones))
    }
}
