package omok.model.stone

import omok.model.adapter.BlackRenjuRuleAdapter
import omok.model.adapter.WhiteRenjuRuleAdapter
import omok.model.game.FoulCondition
import omok.model.stone.StoneColor.BLACK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `돌을 추가할 수 있다`() {
        val stones = Stones(ruleAdapter = BlackRenjuRuleAdapter())
        stones.add(Stone(1, 1, BLACK))

        val actual = stones.stones.size

        val expected = 1

        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `돌의 오목 완성 여부를 알 수 있다`() {
        val stones =
            Stones(
                setOf(
                    Stone(8, 8, BLACK),
                    Stone(8, 9, BLACK),
                    Stone(8, 10, BLACK),
                    Stone(8, 11, BLACK),
                    Stone(8, 12, BLACK),
                ),
                BlackRenjuRuleAdapter(),
            )

        val actual = stones.checkWin(Stones(ruleAdapter = WhiteRenjuRuleAdapter()), Stone(8, 12, BLACK))

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 금수(삼삼) 여부를 알 수 있다`() {
        val stones =
            Stones(
                setOf(
                    Stone(8, 8, BLACK),
                    Stone(9, 8, BLACK),
                    Stone(10, 6, BLACK),
                    Stone(10, 7, BLACK),
                ),
                BlackRenjuRuleAdapter(),
            )

        val actual = stones.checkAnyFoulCondition(Stones(ruleAdapter = BlackRenjuRuleAdapter()), Stone(10, 8, BLACK))

        val expected = FoulCondition.DOUBLE_THREE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 금수(사사) 여부를 알 수 있다`() {
        val stones =
            Stones(
                setOf(
                    Stone(8, 8, BLACK),
                    Stone(9, 8, BLACK),
                    Stone(10, 8, BLACK),
                    Stone(11, 7, BLACK),
                    Stone(11, 6, BLACK),
                    Stone(11, 5, BLACK),
                    Stone(11, 8, BLACK),
                ),
                BlackRenjuRuleAdapter(),
            )

        val actual = stones.checkAnyFoulCondition(Stones(ruleAdapter = BlackRenjuRuleAdapter()), Stone(11, 8, BLACK))

        val expected = FoulCondition.DOUBLE_FOUR

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 금수(장목) 여부를 알 수 있다`() {
        val stones =
            Stones(
                setOf(
                    Stone(8, 8, BLACK),
                    Stone(9, 8, BLACK),
                    Stone(10, 8, BLACK),
                    Stone(11, 8, BLACK),
                    Stone(13, 8, BLACK),
                ),
                BlackRenjuRuleAdapter(),
            )

        val actual = stones.checkAnyFoulCondition(Stones(ruleAdapter = BlackRenjuRuleAdapter()), Stone(12, 8, BLACK))

        val expected = FoulCondition.OVERLINE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 금수가 아닌 여부를 알 수 있다`() {
        val stones =
            Stones(
                setOf(
                    Stone(8, 8, BLACK),
                    Stone(9, 8, BLACK),
                    Stone(10, 8, BLACK),
                    Stone(11, 8, BLACK),
                ),
                BlackRenjuRuleAdapter(),
            )

        val actual = stones.checkAnyFoulCondition(Stones(ruleAdapter = BlackRenjuRuleAdapter()), Stone(11, 8, BLACK))

        val expected = FoulCondition.NONE

        assertThat(actual).isEqualTo(expected)
    }
}
