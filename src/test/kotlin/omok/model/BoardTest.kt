package omok.model

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
        emptyBoard = Board()
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
        val board = initBoard(
            StonePosition(Position(3, 3), playerStone)
        )
        assertThrows<IllegalArgumentException> { board.place(Position(3, 3), player) }
    }

    @Test
    fun `오목이 되면 승리한다`() {
        // given
        val board = initBoard(
            StonePosition(Position(3, 3), playerStone),
            StonePosition(Position(3, 4), playerStone),
            StonePosition(Position(3, 5), playerStone),
            StonePosition(Position(3, 6), playerStone),
            StonePosition(Position(3, 7), playerStone),
        )

        // when
        val actual = board.isWin(Position(3, 7))

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `오목이 안 되면 승리하지 않는다`() {
        // given
        val board = initBoard(
            StonePosition(Position(3, 3), playerStone),
            StonePosition(Position(4, 3), playerStone),
            StonePosition(Position(5, 3), playerStone),
            StonePosition(Position(6, 3), playerStone),
            StonePosition(Position(1, 3), playerStone),
        )

        // when
        val actual = board.isWin(Position(1, 3))

        // then
        assertThat(actual).isFalse
    }
}
