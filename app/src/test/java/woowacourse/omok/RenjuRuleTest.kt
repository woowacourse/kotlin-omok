package woowacourse.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.rule.lib.RenjuRule
import woowacourse.omok.domain.rule.lib.type.Violation

class RenjuRuleTest {
    private lateinit var renjuRule: RenjuRule

    @BeforeEach
    fun setUp() {
        renjuRule = RenjuRule()
    }

    @Test
    fun `A black stone is a foul if it is double three`() {
        // given
        val blackStones =
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
            )
        val whiteStones = listOf(STONE_9I_WHITE)
        val newStonePosition = Position(Row.from(3), Col.from('E'))

        // when
        val expected = renjuRule.checkAnyFoulCondition(blackStones, whiteStones, newStonePosition)

        // then
        assertThat(expected).isEqualTo(Violation.DOUBLE_THREE)
    }

    @ParameterizedTest
    @CsvSource("8, 3", "12, 6", "10, 10", "8, 9", "5, 8")
    fun `A black stone is a foul if it is double four`(
        newStoneRow: Int,
        newStoneCol: Int,
    ) {
        // given
        val blackStones =
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
            )
        val whiteStones =
            listOf(
                STONE_5D_WHITE,
                STONE_9H_WHITE,
            )

        val newStonePosition = Position(Row.from(newStoneRow), Col.from(newStoneCol))

        // when
        val expected = renjuRule.checkAnyFoulCondition(blackStones, whiteStones, newStonePosition)

        assertThat(expected).isEqualTo(Violation.DOUBLE_FOUR)
    }

    @Test
    fun `Black stone is a foul in the case of overline`() {
        // given
        val blackStones =
            listOf(
                STONE_5E_BLACK,
                STONE_6F_BLACK,
                STONE_7G_BLACK,
                STONE_9I_BLACK,
                STONE_10J_BLACK,
            )
        val newStone = STONE_8H_BLACK

        // when
        val expected = renjuRule.checkAnyFoulCondition(blackStones, emptyList(), newStone.position)

        // then
        assertThat(expected).isEqualTo(Violation.OVERLINE)
    }

    @Test
    fun `If 5 black stones are in a row, it is win even if it is double four`() {
        // given
        val blackStones =
            listOf(
                STONE_5E_BLACK,
                STONE_5F_BLACK,
                STONE_5H_BLACK,
                STONE_5I_BLACK,
                STONE_6F_BLACK,
                STONE_6H_BLACK,
                STONE_4F_BLACK,
                STONE_4H_BLACK,
                STONE_3E_BLACK,
                STONE_3I_BLACK,
            )
        val newStone = STONE_5G_BLACK

        // when
        val expected = renjuRule.checkWin(blackStones, emptyList(), newStone.position)

        // then
        assertThat(expected).isTrue
    }

    @Test
    fun `If 5 black stones are in a row, it is win even if it is double three`() {
        // given
        val blackStones =
            listOf(
                STONE_5E_BLACK,
                STONE_5G_BLACK,
                STONE_5H_BLACK,
                STONE_5I_BLACK,
                STONE_4E_BLACK,
                STONE_4F_BLACK,
                STONE_6F_BLACK,
                STONE_6G_BLACK,
            )
        val newStone = STONE_5F_BLACK

        // when
        val expected = renjuRule.checkWin(blackStones, emptyList(), newStone.position)

        // then
        assertThat(expected).isTrue
    }
}
