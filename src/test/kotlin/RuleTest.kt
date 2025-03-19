
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RuleTest {
    @Test
    fun `같은 색 가로 돌이 5개 이상인지 판단할 수 있다`() {
        val rule = Rule()
        val stone = STONE_1A_BLACK
        val winningStones = listOf(STONE_1C_BLACK, STONE_1B_BLACK, STONE_1D_BLACK, STONE_1E_BLACK)
        val nothingStones = listOf(STONE_1C_BLACK, STONE_1B_WHITE, STONE_1D_BLACK, STONE_1E_BLACK)
        assertAll(
            { assertTrue(rule.isHorizontalWin(stone, winningStones)) },
            { assertFalse(rule.isHorizontalWin(stone, nothingStones)) },
        )
    }

    @Test
    fun `세로 돌이 5개 이상인지 판단할 수 있다`() {
        val rule = Rule()
        val stone = STONE_3A_BLACK
        val winningStones = listOf(STONE_1A_BLACK, STONE_2A_BLACK, STONE_4A_BLACK, STONE_5A_BLACK)
        val nothingStones = listOf(STONE_1A_BLACK, STONE_2A_WHITE, STONE_4A_BLACK, STONE_5A_BLACK)
        assertAll(
            { assertTrue(rule.isVerticalWin(stone, winningStones)) },
            { assertFalse(rule.isVerticalWin(stone, nothingStones)) },
        )
    }
}
