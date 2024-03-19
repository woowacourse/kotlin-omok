package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `오목판에 돌을 놓는다`() {
        board.place(Position(3, 3), Stone.BLACK)

        val actual = board.find(Position(3, 3))
        assertThat(actual).isEqualTo(Stone.BLACK)
    }

    @Test
    fun `오목판에 이미 돌이 있는 곳에 놓으면 예외가 발생한다`() {
        board.place(Position(3, 3), Stone.BLACK)

        assertThrows<IllegalArgumentException> { board.place(Position(3, 3), Stone.BLACK) }
    }

    @Test
    fun `오목이 되면 승리한다`() {
        // given
        board.place(Position(3, 3), Stone.WHITE)
        board.place(Position(3, 4), Stone.WHITE)
        board.place(Position(3, 5), Stone.WHITE)
        board.place(Position(3, 6), Stone.WHITE)

        // when
        val position = Position(3, 7)
        board.place(position, Stone.WHITE)

        // then
        assertThat(board.isWin(position)).isTrue
    }

    @Test
    fun `오목이 안 되면 승리하지 않는다`() {
        // given
        board.place(Position(3, 3), Stone.BLACK)
        board.place(Position(4, 3), Stone.BLACK)
        board.place(Position(5, 3), Stone.BLACK)
        board.place(Position(6, 3), Stone.BLACK)

        // when
        val position = Position(1, 3)
        board.place(position, Stone.BLACK)

        // then
        assertThat(board.isWin(position)).isFalse
    }
}
