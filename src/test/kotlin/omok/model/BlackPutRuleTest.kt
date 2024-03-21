package omok.model

import io.kotest.matchers.booleans.shouldBeFalse
import omok.fixtures.createBlackBoard
import omok.fixtures.createBlackStone
import omok.fixtures.createPoint
import omok.model.rule.BlackPutRule
import org.junit.jupiter.api.Test

class BlackPutRuleTest {
    @Test
    fun `장목이면 금수다`() {
        // given
        val blackBoard = createBlackBoard(
            createPoint(1, 1),
            createPoint(1, 2),
            createPoint(1, 3),
            createPoint(1, 4),
            createPoint(1, 5),
        )
        val blackStone = createBlackStone(1, 6)
        // when
        val canPut = BlackPutRule.canPut(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `3-3이면 금수다`() {
        // given
        val blackBoard = createBlackBoard(
            createPoint(1, 2),
            createPoint(1, 3),
            createPoint(2, 1),
            createPoint(3, 1),
        )
        val blackStone = createBlackStone(1, 1)
        // when
        val canPut = BlackPutRule.canPut(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `4-4이면 금수다`() {
        val blackBoard = createBlackBoard(
            createPoint(1, 2),
            createPoint(1, 3),
            createPoint(1, 4),
            createPoint(2, 1),
            createPoint(3, 1),
            createPoint(4, 1)
        )
        val blackStone = createBlackStone(1, 1)
        // when
        val canPut = BlackPutRule.canPut(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }
}
