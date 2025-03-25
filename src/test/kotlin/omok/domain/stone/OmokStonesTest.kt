package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokRule
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

class OmokStonesTest {
    private val omokRule = OmokRule()

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

        doubleThreeCases.forEach { (blackPoints, point) ->
            val stones = OmokStones(omokRule, blackPoints.toBlackStones())
            // then
            assertThat(stones.isFoul(Stone(StoneColor.BLACK, point))).isTrue()
        }
    }

    @Test
    fun `흑돌이 4-4이면 돌을 놓을 수 없다`() {
        val stones = OmokStones(omokRule, DOUBLE_FOUR.toBlackStones())
        assertThat(stones.isFoul(F12.toBlackStone())).isTrue()
    }

    @Test
    fun `흑돌이 장목이면 돌을 놓을 수 없다`() {
        val stones = OmokStones(omokRule, OVERLINE.toBlackStones())
        assertThat(stones.isFoul(H8.toBlackStone())).isTrue()
    }

    private fun Set<Point>.toBlackStones(): Set<Stone> = this.map { Stone(StoneColor.BLACK, it) }.toSet()

    private fun Point.toBlackStone(): Stone = Stone(StoneColor.BLACK, this)
}
