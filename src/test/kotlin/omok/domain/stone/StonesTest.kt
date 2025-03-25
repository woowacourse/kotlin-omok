package omok.domain.stone

import omok.domain.rule.BlackStoneRule
import omok.domain.rule.WhiteStoneRule
import omok.fixture.A1
import omok.fixture.A2
import omok.fixture.DOUBLE_FOUR
import omok.fixture.DOUBLE_THREE_A
import omok.fixture.DOUBLE_THREE_B
import omok.fixture.DOUBLE_THREE_C
import omok.fixture.DOUBLE_THREE_D
import omok.fixture.F12
import omok.fixture.H8
import omok.fixture.OVERLINE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    private val blackStoneRule = BlackStoneRule()
    private val whiteStoneRule = WhiteStoneRule()

    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val stones = Stones(blackStoneRule, setOf(A1, A2))
        val expected = A2
        assertThat(stones.lastStonePoint()).isEqualTo(expected)
    }

    @Test
    fun `흑돌이 3-3이면 돌을 놓을 수 없다`() {
        // given
        val doubleThreeCases =
            listOf(
                DOUBLE_THREE_A,
                DOUBLE_THREE_B,
                DOUBLE_THREE_C,
                DOUBLE_THREE_D,
            )

        val whiteStones = Stones(whiteStoneRule, setOf())
        doubleThreeCases.forEach { (blackPoints, point) ->
            val blackStones = Stones(blackStoneRule, blackPoints)
            // then
            assertThat(blackStones.isFoul(whiteStones, point)).isTrue()
        }
    }

    @Test
    fun `흑돌이 4-4이면 돌을 놓을 수 없다`() {
        val blackStones = Stones(blackStoneRule, DOUBLE_FOUR)
        val whiteStones = Stones(whiteStoneRule, setOf())
        assertThat(blackStones.isFoul(whiteStones, F12)).isTrue()
    }

    @Test
    fun `흑돌이 장목이면 돌을 놓을 수 없다`() {
        val blackStones = Stones(blackStoneRule, OVERLINE)
        val whiteStones = Stones(whiteStoneRule, setOf())
        assertThat(blackStones.isFoul(whiteStones, H8)).isTrue()
    }
}
