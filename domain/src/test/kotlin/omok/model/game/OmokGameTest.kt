package omok.model.game

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import omock.model.Position
import omock.model.isFailure
import omock.model.isSuccess
import omok.fixtures.createBlackBlock
import omok.fixtures.createBlocks
import omok.fixtures.createOmokGame
import omok.fixtures.createWhiteBlock
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `오목판이 비어있으면 흑이 먼저 돌을 놓는다`() {
        // given
        val game = createOmokGame()
        val expectedBlock = createBlackBlock(1, 1)
        // when
        val position = Position(1, 1)
        val placeSuccess = game.placeStone(position).isSuccess()
        val lastBlock = game.lastGameResult().lastBlock
        // then
        assertSoftly {
            placeSuccess.shouldBeTrue()
            lastBlock shouldBe expectedBlock
        }
    }

    @Test
    fun `오목알을 놓고, 오목이 완성되면 게임이 끝난다`() {
        // given
        val game =
            createOmokGame(
                createBlackBlock(1, 1),
                createBlackBlock(1, 2),
                createBlackBlock(1, 3),
                createBlackBlock(1, 4),
                createWhiteBlock(1, 6),
            )
        val expectedRecords =
            createBlocks(
                createBlackBlock(1, 1),
                createBlackBlock(1, 2),
                createBlackBlock(1, 3),
                createBlackBlock(1, 4),
                createWhiteBlock(1, 6),
                createBlackBlock(1, 5),
            )
        // when
        val position = Position(1, 5)
        val placeSuccess = game.placeStone(position).isSuccess()
        val lastBlock = game.lastGameResult().lastBlock
        val records = game.lastGameResult().board.blockRecords
        val isEnd = game.isEnd()
        // then
        assertSoftly {
            placeSuccess.shouldBeTrue()
            lastBlock shouldBe createBlackBlock(1, 5)
            records shouldBe expectedRecords
            isEnd.shouldBeTrue()
        }
    }

    @Test
    fun `오목이 완성되었으면 돌을 놓을 수 없다`() {
        // given
        val game =
            createOmokGame(
                createBlackBlock(1, 1),
                createBlackBlock(1, 2),
                createBlackBlock(1, 3),
                createBlackBlock(1, 4),
                createBlackBlock(1, 5),
            )
        // when
        val position = Position(1, 6)
        val isPlaceFail = game.placeStone(position).isFailure()
        val isEnd = game.isEnd()
        // then
        isPlaceFail.shouldBeTrue()
        isEnd.shouldBeTrue()
    }
}
