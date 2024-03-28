package omok.model.game.state

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import omok.fixtures.createBoard
import omok.fixtures.createPosition
import omok.fixtures.createWhiteStone
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    @Test
    fun `오목알을 놓고, 오목이 완성되지 않으면 다음 플레이어 턴으로 넘어간다`() {
        // given
        val board = createBoard()
        val whiteTurn = BlackTurn(board)
        val expect = WhiteTurn::class.java
        // when
        val actual = whiteTurn.placeStone(createPosition(1, 1))
        val hasOmok = actual.hasOmok()
        // then
        actual shouldNotBeSameInstanceAs expect
        hasOmok.shouldBeFalse()
    }

    @Test
    fun `오목알을 놓고, 오목이 완성되면 게임이 끝난다`() {
        // given
        val board =
            createBoard(
                createWhiteStone(1, 1),
                createWhiteStone(1, 2),
                createWhiteStone(1, 3),
                createWhiteStone(1, 4),
            )
        val whiteTurn = WhiteTurn(board)
        val expect = Finish::class.java
        // when
        val actual = whiteTurn.placeStone(createPosition(1, 5))
        val hasOmok = actual.hasOmok()
        // then
        actual shouldNotBeSameInstanceAs expect
        hasOmok.shouldBeTrue()
    }
}
