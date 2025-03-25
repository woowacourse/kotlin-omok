package omok.domain.stone

import omok.fixture.C13
import omok.fixture.DOUBLE_FOUR
import omok.fixture.DOUBLE_THREE_A
import omok.fixture.DOUBLE_THREE_B
import omok.fixture.DOUBLE_THREE_C
import omok.fixture.DOUBLE_THREE_D
import omok.fixture.H8
import omok.fixture.OVERLINE
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlackStonesTest {
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

        val whiteStones = WhiteStones(setOf())
        doubleThreeCases.forEach { (blackPoints, point) ->
            val blackStones = BlackStones(blackPoints)
            // then
            Assertions.assertThat(blackStones.isFoul(whiteStones, point)).isTrue()
        }
    }

    @Test
    fun `흑돌이 4-4이면 돌을 놓을 수 없다`() {
        val blackStones = BlackStones(DOUBLE_FOUR)
        val whiteStones = WhiteStones(setOf())
        Assertions.assertThat(blackStones.isFoul(whiteStones, C13)).isTrue()
    }

    @Test
    fun `흑돌이 장목이면 돌을 놓을 수 없다`() {
        val blackStones = BlackStones(OVERLINE)
        val whiteStones = WhiteStones(setOf())
        Assertions.assertThat(blackStones.isFoul(whiteStones, H8)).isTrue()
    }
}
