package omok.domain

import omok.domain.rule.OmokRule
import omok.domain.stone.OmokStones
import omok.domain.stone.Stone
import omok.domain.stone.StoneColor
import omok.fixture.DOUBLE_FOUR
import omok.fixture.DOUBLE_THREE_A
import omok.fixture.DOUBLE_THREE_B
import omok.fixture.DOUBLE_THREE_C
import omok.fixture.DOUBLE_THREE_D
import omok.fixture.F12
import omok.fixture.H8
import omok.fixture.OVERLINE
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
            assertThrows<IllegalArgumentException> { board.checkViolation(stone) }
        }
    }

    @Test
    fun `흑돌이 4-4이면 돌을 놓을 수 없다`() {
        val stones = OmokStones(DOUBLE_FOUR.toBlackStones())
        val board = OmokBoard(rule = omokRule, stones = stones)
        assertThrows<IllegalArgumentException> { board.checkViolation(F12.toBlackStone()) }
    }

    @Test
    fun `흑돌이 장목이면 돌을 놓을 수 없다`() {
        val stones = OmokStones(OVERLINE.toBlackStones())
        val board = OmokBoard(rule = omokRule, stones = stones)
        assertThrows<IllegalArgumentException> { board.checkViolation(H8.toBlackStone()) }
    }

    private fun Set<Point>.toBlackStones(): Set<Stone> = this.map { Stone(StoneColor.BLACK, it) }.toSet()

    private fun Point.toBlackStone(): Stone = Stone(StoneColor.BLACK, this)
}
