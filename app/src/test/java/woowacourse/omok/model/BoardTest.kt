package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

class BoardTest {
    private lateinit var emptyBoard: Board
    private val playerStone = Stone.BLACK

    @BeforeEach
    fun setUp() {
        emptyBoard = Board()
    }

    @Test
    fun `오목판에 돌을 놓는다2`() {
        emptyBoard.place(StonePosition(Position(3, 3), playerStone))

        val actual = emptyBoard.find(Position(3, 3))
        assertThat(actual).isEqualTo(playerStone)
    }

    @Test
    fun `비어있는 위치인지 확인한다`() {
        val actual = emptyBoard.emptyPosition(Position(4, 4))

        assertThat(actual).isTrue
    }
}
