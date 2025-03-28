package woowacourse.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.omok.domain.rule.lib.OmokRule
import woowacourse.omok.domain.rule.lib.type.Violation

class OmokRuleTest {
    private lateinit var omokRule: OmokRule

    @BeforeEach
    fun setUp() {
        omokRule = OmokRule()
    }

    @Test
    fun `중복된 위치인지 판단한다`() {
        val blackStones = listOf(STONE_5E_BLACK, STONE_5H_BLACK)
        val whiteStones = listOf(STONE_5G_WHITE)
        val duplicatedPosition = STONE_5H_WHITE.position
        val duplicatedExpected = Violation.DUPLICATE_POSITION
        val nonDuplicatedPosition = STONE_10A_WHITE.position
        val nonDuplicatedExpected = Violation.NONE

        val duplicatedActual = omokRule.checkDuplicatePosition(blackStones, whiteStones, duplicatedPosition)
        val nonDuplicatedActual = omokRule.checkDuplicatePosition(blackStones, whiteStones, nonDuplicatedPosition)

        assertAll(
            { assertThat(duplicatedActual).isEqualTo(duplicatedExpected) },
            { assertThat(nonDuplicatedActual).isEqualTo(nonDuplicatedExpected) },
        )
    }

    @Test
    fun `승리 여부를 판단한다`() {
        val whiteStones = listOf(STONE_5A_WHITE, STONE_5B_WHITE, STONE_5C_WHITE, STONE_5D_WHITE, STONE_5E_WHITE)

        val actual =
            omokRule.checkWin(
                targetStone = whiteStones,
                otherStones = emptyList(),
                startPosition = STONE_5E_WHITE.position,
            )

        assertThat(actual).isTrue()
    }
}
