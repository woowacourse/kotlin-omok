import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

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
    fun `같은 색 세로 돌이 5개 이상인지 판단할 수 있다`() {
        val rule = Rule()
        val stone = STONE_3A_BLACK
        val winningStones = listOf(STONE_1A_BLACK, STONE_2A_BLACK, STONE_4A_BLACK, STONE_5A_BLACK)
        val nothingStones = listOf(STONE_1A_BLACK, STONE_2A_WHITE, STONE_4A_BLACK, STONE_5A_BLACK)
        assertAll(
            { assertTrue(rule.isVerticalWin(stone, winningStones)) },
            { assertFalse(rule.isVerticalWin(stone, nothingStones)) },
        )
    }

    @Test
    fun `증가하는 대각선 같은 색 돌이 5개 이상인지 판단할 수 있다`() {
        val rule = Rule()
        val stone = STONE_3C_BLACK
        val winningStones = listOf(STONE_1A_BLACK, STONE_2B_BLACK, STONE_4D_BLACK, STONE_5E_BLACK)
        val nothingStones = listOf(STONE_1A_BLACK, STONE_2B_WHITE, STONE_4D_BLACK, STONE_5E_BLACK)
        assertAll(
            { assertTrue(rule.isIncreasingDiagonalWin(stone, winningStones)) },
            { assertFalse(rule.isIncreasingDiagonalWin(stone, nothingStones)) },
        )
    }

    @Test
    fun `감소하는 대각선 같은 색 돌이 5개 이상인지 판단할 수 있다`() {
        val rule = Rule()
        val stone = STONE_3C_BLACK
        val winningStones = listOf(STONE_1E_BLACK, STONE_2D_BLACK, STONE_4B_BLACK, STONE_5A_BLACK)
        val nothingStones = listOf(STONE_1E_BLACK, STONE_2B_WHITE, STONE_4B_BLACK, STONE_5A_BLACK)
        assertAll(
            { assertTrue(rule.isDecreasingDiagonalWin(stone, winningStones)) },
            { assertFalse(rule.isDecreasingDiagonalWin(stone, nothingStones)) },
        )
    }

    @Test
    fun `흑돌일 때 삼삼을 판단할 수 있다`() {
        val rule = Rule()
        val stones =
            listOf(
                STONE_3C_BLACK,
                STONE_3D_BLACK,
                STONE_4E_BLACK,
                STONE_5C_BLACK,
                STONE_12C_BLACK,
                STONE_12E_BLACK,
                STONE_13D_BLACK,
                STONE_14D_BLACK,
                STONE_6B_BLACK,
                STONE_5E_BLACK,
                STONE_6E_BLACK,
                STONE_3K_BLACK,
                STONE_6K_BLACK,
                STONE_4M_BLACK,
                STONE_4N_BLACK,
                STONE_9N_BLACK,
                STONE_10M_BLACK,
                STONE_12M_BLACK,
                STONE_9J_BLACK,
                STONE_9I_WHITE,
            )
        val newStone = STONE_3E_BLACK
        assertTrue(rule.checkThreeThreeFoulByAllDirections(newStone, stones))
    }

    @ParameterizedTest
    @CsvSource("8, 3", "12, 6", "10, 10", "8, 9", "5, 8")
    fun `흑돌일 때 사사를 판단할 수 있다`(
        newStoneRow: Int,
        newStoneCol: Int,
    ) {
        // given
        val rule = Rule()
        val stones =
            listOf(
                STONE_15C_BLACK,
                STONE_14C_BLACK,
                STONE_12C_BLACK,
                STONE_11C_BLACK,
                STONE_10C_BLACK,
                STONE_12D_BLACK,
                STONE_12G_BLACK,
                STONE_12I_BLACK,
                STONE_12J_BLACK,
                STONE_9J_BLACK,
                STONE_8J_BLACK,
                STONE_6J_BLACK,
                STONE_8K_BLACK,
                STONE_8H_BLACK,
                STONE_7H_BLACK,
                STONE_6H_BLACK,
                STONE_6E_BLACK,
                STONE_5E_BLACK,
                STONE_5F_BLACK,
                STONE_5G_BLACK,
                STONE_4G_BLACK,
                STONE_5D_WHITE,
                STONE_9H_WHITE,
            )

        val newStone = Stone(Position(Row.from(newStoneRow), Col.fromInt(newStoneCol)), StoneColor.BLACK)

        assertTrue(rule.checkFourFoulByAllDirections(newStone, stones))
    }
}
