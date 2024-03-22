package omok.model

import io.kotest.matchers.booleans.shouldBeFalse
import omok.fixtures.createBlackStone
import omok.fixtures.createBoard
import omok.model.rule.RenjuRule
import org.junit.jupiter.api.Test

class BlackOmokGameRuleTest {
    @Test
    fun `장목이면 금수다`() {
        // given
        val blackBoard =
            createBoard(
                createBlackStone(1, 1),
                createBlackStone(1, 2),
                createBlackStone(1, 3),
                createBlackStone(1, 4),
                createBlackStone(1, 5),
            )
        val blackStone = createBlackStone(1, 6)
        // when
        val canPut = RenjuRule.canPlaceStone(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `3-3이면 금수다`() {
        // given
        val blackBoard =
            createBoard(
                createBlackStone(4, 5),
                createBlackStone(4, 6),
                createBlackStone(5, 4),
                createBlackStone(6, 4),
            )
        val blackStone = createBlackStone(4, 4)
        // when
        val canPut = RenjuRule.canPlaceStone(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `4-4이면 금수다`() {
        val blackBoard =
            createBoard(
                createBlackStone(1, 2),
                createBlackStone(1, 3),
                createBlackStone(1, 4),
                createBlackStone(2, 1),
                createBlackStone(3, 1),
                createBlackStone(4, 1),
            )
        val blackStone = createBlackStone(1, 1)
        // when
        val canPut = RenjuRule.canPlaceStone(blackStone, blackBoard)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `이미 알이 있으면 금수`() {
        val board =
            createBoard(
                createBlackStone(1, 2),
            )
        val stone = createBlackStone(1, 2)
        // when
        val canPut = RenjuRule.canPlaceStone(stone, board)
        // then
        canPut.shouldBeFalse()
    }

    @Test
    fun `범위 밖에 놓으면 금수`() {
        val board = createBoard()
        val stone = createBlackStone(0, 0)
        // when
        val canPut = RenjuRule.canPlaceStone(stone, board)
        // then
        canPut.shouldBeFalse()
    }
}
