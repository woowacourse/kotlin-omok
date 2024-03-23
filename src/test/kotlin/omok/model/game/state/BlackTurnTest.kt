package omok.model.game.state

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import omok.fixtures.createBlackStone
import omok.fixtures.createBoard
import omok.fixtures.createPosition
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `오목알을 놓고, 오목이 완성되지 않으면 다음 플레이어 턴으로 넘어간다`() {
        // given
        val board = createBoard()
        val blackTurn = BlackTurn(board)
        val expect = WhiteTurn::class.java
        // when
        val actual = blackTurn.placeStone { createPosition(1, 1) }
        val isFinished = actual.isFinished
        // then
        actual shouldNotBeSameInstanceAs expect
        isFinished.shouldBeFalse()
    }

    @Test
    fun `오목알을 놓고, 오목이 완성되면 게임이 끝난다`() {
        // given
        val board =
            createBoard(
                createBlackStone(1, 1),
                createBlackStone(1, 2),
                createBlackStone(1, 3),
                createBlackStone(1, 4),
            )
        val blackTurn = BlackTurn(board)
        val expect = Finish::class.java
        // when
        val actual = blackTurn.placeStone { createPosition(1, 5) }
        val isFinished = actual.isFinished
        // then
        actual shouldNotBeSameInstanceAs expect
        isFinished.shouldBeTrue()
    }
}
