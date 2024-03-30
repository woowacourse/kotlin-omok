package woowacourse.omok.model

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.player.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var emptyBoard: Board
    private lateinit var player: Player
    private val playerStone = Stone.BLACK

    @BeforeEach
    fun setUp() {
        emptyBoard = Board(15)
        player = Player(playerStone)
    }

    @Test
    fun `오목판에 돌을 놓는다`() {
        emptyBoard.place(Position(3, 3), player)

        val actual = emptyBoard.find(Position(3, 3))
        assertThat(actual).isEqualTo(playerStone)
    }

    @Test
    fun `오목판에 이미 돌이 있는 곳에 놓으면 예외가 발생한다`() {
        val board = initBoard(StonePosition(Position(3, 3), playerStone))
        assertThrows<IllegalArgumentException> { board.place(Position(3, 3), player) }
    }

    @Test
    fun `오목판에 벗어나는 위치에 돌을 놓으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { emptyBoard.place(Position(20, 3), player) }
    }

    @Test
    fun `오목판에 돌을 놓을 수 있는 곳이 있는지 확인한다`() {
        // given
        val board =
            initBoard(
                StonePosition(Position(3, 3), playerStone),
                StonePosition(Position(1, 3), playerStone),
                StonePosition(Position(5, 5), playerStone),
            )

        // when
        val actual = board.isFull()

        // then
        assertThat(actual).isFalse
    }

    @Test
    fun `오목판이 돌로 가득 차있는지 확인한다`() {
        // given
        val board =
            initBoard(
                StonePosition(Position(0, 0), playerStone),
                StonePosition(Position(0, 1), playerStone),
                StonePosition(Position(1, 0), playerStone),
                StonePosition(Position(1, 1), playerStone),
                size = 2,
            )

        // when
        val actual = board.isFull()

        // then
        assertThat(actual).isTrue
    }
}
