package omok.model.board

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import omock.model.Position
import omock.model.board.BoardSize
import org.junit.jupiter.api.Test

class OmokBoardSizeTest {
    @Test
    fun `BoardSize 의 크기는 5 이상이다`() {
        assertSoftly {
            shouldThrow<IllegalArgumentException> { BoardSize(4) }
            shouldNotThrow<IllegalArgumentException> { BoardSize(5) }
        }
    }

    @Test
    fun `BoardSize 의 Bound 안에 있는지 확인할 수 있다`() {
        val boardSize = BoardSize(5)
        assertSoftly {
            boardSize.isInBounds(Position(0, 0)).shouldBeFalse()
            boardSize.isInBounds(Position(1, 1)).shouldBeTrue()
            boardSize.isInBounds(Position(1, 1)).shouldBeTrue()
        }
    }
}
