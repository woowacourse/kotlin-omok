import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Foul
import rule.type.Violation
import rule.wrapper.position.Position

class WhiteRenjuRuleTest {
    private lateinit var renjuRule: OmokRule

    @BeforeEach
    fun setUp() {
        renjuRule = WhiteRenjuRule(15, 15)
    }

    @ParameterizedTest
    @CsvSource("3, 5", "12, 4", "3, 5", "4, 11", "11, 12")
    fun `White stone is not a foul even if it is double three`() {
        val blackStones = listOf(STONE_9I_BLACK)
        val whiteStones =
            listOf(
                STONE_3C_WHITE, STONE_3D_WHITE, STONE_4D_WHITE,
                STONE_5C_WHITE, STONE_12C_WHITE, STONE_12E_WHITE,
                STONE_13D_WHITE, STONE_14D_WHITE, STONE_6B_WHITE,
                STONE_5E_WHITE, STONE_6E_WHITE, STONE_3K_WHITE,
                STONE_6K_WHITE, STONE_4M_WHITE, STONE_4N_WHITE,
                STONE_9N_WHITE, STONE_10M_WHITE, STONE_12M_WHITE,
                STONE_9J_WHITE,
            )
        val newStone = STONE_3E_WHITE

        val expected = renjuRule.checkDoubleFoul(blackStones, whiteStones, newStone.position, Foul.DOUBLE_THREE)

        assertThat(expected).isEqualTo(Violation.NONE)
    }

    @ParameterizedTest
    @CsvSource("8, 3", "12, 6", "10, 10", "8, 9", "5, 8")
    fun `White stone is not a foul even if it is double four`(
        newStoneRow: Int,
        newStoneCol: Int,
    ) {
        val blackStones =
            listOf(
                STONE_5D_BLACK,
                STONE_9H_BLACK,
            )
        val whiteStones =
            listOf(
                STONE_15C_WHITE, STONE_14C_WHITE, STONE_12C_WHITE,
                STONE_11C_WHITE, STONE_10C_WHITE, STONE_12D_WHITE,
                STONE_12G_WHITE, STONE_12I_WHITE, STONE_12J_WHITE,
                STONE_9J_WHITE, STONE_8J_WHITE, STONE_6J_WHITE,
                STONE_8K_WHITE, STONE_8H_WHITE, STONE_7H_WHITE,
                STONE_6H_WHITE, STONE_6E_WHITE, STONE_5E_WHITE,
                STONE_5F_WHITE, STONE_5G_WHITE, STONE_4G_WHITE,
            )
        val newStone = Position(Row.from(newStoneRow), Col.fromInt(newStoneCol))

        val expected = renjuRule.checkAnyFoulCondition(blackStones, whiteStones, newStone)

        assertThat(expected).isEqualTo(Violation.NONE)
    }

    @Test
    fun `White stone is not a foul even if it is a overline`() {
        val whiteStones =
            listOf(
                STONE_5E_WHITE,
                STONE_6F_WHITE,
                STONE_7G_WHITE,
                STONE_9I_WHITE,
                STONE_10J_WHITE,
            )

        val newStone = STONE_8H_WHITE

        val expected = renjuRule.checkOverline(whiteStones, newStone.position)

        assertThat(expected).isEqualTo(Violation.NONE)
    }
}
