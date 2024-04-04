package omok.model

import io.kotest.matchers.booleans.shouldBeFalse
import omok.fixtures.createBlackStone
import omok.fixtures.createPosition
import omok.fixtures.createWhiteStone
import org.junit.jupiter.api.Test
import woowacourse.omok.fixtures.createBlackBoard
import woowacourse.omok.fixtures.createWhiteBoard
import woowacourse.omok.model.rule.RenjuRule

class RenjuRuleTest {
    @Test
    fun `장목이면 금수다`() {
        // given
        val blackBoard =
            createBlackBoard(
                createPosition(1, 1),
                createPosition(1, 2),
                createPosition(1, 3),
                createPosition(1, 4),
                createPosition(1, 5),
            )
        val blackStone = createBlackStone(1, 6)
        // when
        val canPut = RenjuRule.canPlace(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `3-3이면 금수다`() {
        // given
        val blackBoard =
            createBlackBoard(
                createPosition(4, 5),
                createPosition(4, 6),
                createPosition(5, 4),
                createPosition(6, 4),
            )
        val blackStone = createBlackStone(4, 4)
        // when
        val canPut = RenjuRule.canPlace(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `4-4이면 금수다`() {
        val blackBoard =
            createBlackBoard(
                createPosition(1, 2),
                createPosition(1, 3),
                createPosition(1, 4),
                createPosition(2, 1),
                createPosition(3, 1),
                createPosition(4, 1),
            )
        val blackStone = createBlackStone(1, 1)
        // when
        val canPut = RenjuRule.canPlace(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `이미 알이 있으면 금수`() {
        val whiteBoard =
            createWhiteBoard(
                createPosition(1, 2),
            )
        val whiteStone = createWhiteStone(1, 2)
        // when
        val canPut = RenjuRule.canPlace(whiteStone, whiteBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `범위 밖에 놓으면 금수`() {
        val whiteBoard = createWhiteBoard()
        val whiteStone = createWhiteStone(0, 0)
        // when
        val canPut = RenjuRule.canPlace(whiteStone, whiteBoard)
        // then
        canPut.shouldBeFalse()
    }
}
