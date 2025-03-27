package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.rule.Violation
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.fixture.DOUBLE_FOUR
import woowacourse.omok.fixture.DOUBLE_THREE_A
import woowacourse.omok.fixture.DOUBLE_THREE_B
import woowacourse.omok.fixture.DOUBLE_THREE_C
import woowacourse.omok.fixture.DOUBLE_THREE_D
import woowacourse.omok.fixture.F12
import woowacourse.omok.fixture.H8
import woowacourse.omok.fixture.OVERLINE

class OmokBoardTest {
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
            val stones = OmokStones(blackPoints.toBlackStones())
            val board = OmokBoard(rule = omokRule, stones = stones)
            // then
            val stone = Stone(StoneColor.BLACK, point)
            assertThat(board.checkViolation(stone)).isEqualTo(Violation.DOUBLE_THREE)
        }
    }

    @Test
    fun `흑돌이 4-4이면 돌을 놓을 수 없다`() {
        val stones = OmokStones(DOUBLE_FOUR.toBlackStones())
        val board = OmokBoard(rule = omokRule, stones = stones)
        assertThat(board.checkViolation(F12.toBlackStone())).isEqualTo(Violation.DOUBLE_FOUR)
    }

    @Test
    fun `흑돌이 장목이면 돌을 놓을 수 없다`() {
        val stones = OmokStones(OVERLINE.toBlackStones())
        val board = OmokBoard(rule = omokRule, stones = stones)
        assertThat(board.checkViolation(H8.toBlackStone())).isEqualTo(Violation.OVERLINE)
    }

    private fun Set<Point>.toBlackStones(): Set<Stone> = this.map { Stone(StoneColor.BLACK, it) }.toSet()

    private fun Point.toBlackStone(): Stone = Stone(StoneColor.BLACK, this)
}
