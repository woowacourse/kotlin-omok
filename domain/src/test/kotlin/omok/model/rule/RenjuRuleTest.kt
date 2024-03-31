package omok.model.rule

import io.kotest.matchers.shouldBe
import omock.model.InvalidDuplicatedPlaced
import omock.model.InvalidFourFourRule
import omock.model.InvalidGameOver
import omock.model.InvalidOutOfBound
import omock.model.InvalidOverLineRule
import omock.model.InvalidThreeThreeRule
import omock.model.Position
import omock.model.Stone
import omock.model.rule.RenjuRule
import omok.fixtures.createBlackBlock
import omok.fixtures.createBoard
import omok.fixtures.createWhiteBlock
import org.junit.jupiter.api.Test

class RenjuRuleTest {
    @Test
    fun `검은돌 - 장목이면 금수다`() {
        // given
        val blackBoard =
            createBoard(
                createBlackBlock(1, 1),
                createBlackBlock(1, 2),
                createBlackBlock(1, 3),
                createBlackBlock(1, 4),
                createBlackBlock(1, 6),
            )
        // when
        val position = Position(1, 5)
        val stone = Stone.BLACK
        val result = RenjuRule.placeStone(position, stone, blackBoard)
        // then
        result shouldBe InvalidOverLineRule
    }

    @Test
    fun `검은돌 - 3-3이면 금수다`() {
        // given
        val blackBoard =
            createBoard(
                createBlackBlock(4, 5),
                createBlackBlock(4, 6),
                createBlackBlock(5, 4),
                createBlackBlock(6, 4),
            )
        // when
        val position = Position(4, 4)
        val stone = Stone.BLACK
        val result = RenjuRule.placeStone(position, stone, blackBoard)
        // then
        result shouldBe InvalidThreeThreeRule
    }

    @Test
    fun `검은돌 - 4-4이면 금수다`() {
        // given
        val blackBoard =
            createBoard(
                createBlackBlock(1, 2),
                createBlackBlock(1, 3),
                createBlackBlock(1, 4),
                createBlackBlock(2, 1),
                createBlackBlock(3, 1),
                createBlackBlock(4, 1),
            )
        // when
        val position = Position(1, 1)
        val stone = Stone.BLACK
        val result = RenjuRule.placeStone(position, stone, blackBoard)
        // then
        result shouldBe InvalidFourFourRule
    }

    @Test
    fun `모든 돌 - 이미 알이 있으면 금수`() {
        val board =
            createBoard(
                createWhiteBlock(1, 2),
            )
        // when
        val position = Position(1, 2)
        val stone = Stone.WHITE
        val result = RenjuRule.placeStone(position, stone, board)
        // then
        result shouldBe InvalidDuplicatedPlaced
    }

    @Test
    fun `모든 돌 - 범위 밖에 놓으면 금수`() {
        val board = createBoard()
        // when
        val position = Position(0, 0)
        val stone = Stone.BLACK
        val result = RenjuRule.placeStone(position, stone, board)
        // then
        result shouldBe InvalidOutOfBound
    }

    @Test
    fun `모든 돌 - 이미 오목이 있으면 금수`() {
        // given
        val board =
            createBoard(
                createBlackBlock(1, 1),
                createBlackBlock(1, 2),
                createBlackBlock(1, 3),
                createBlackBlock(1, 4),
                createBlackBlock(1, 5),
            )
        // when
        val position = Position(0, 0)
        val stone = Stone.WHITE
        val result = RenjuRule.placeStone(position, stone, board)
        // then
        result shouldBe InvalidGameOver
    }
}
