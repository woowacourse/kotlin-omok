package omok.model

import omok.model.rule.StoneForbiddenPlaces
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(
            StoneForbiddenPlaces(
                blackForbiddenPlaces = listOf(
                    DoubleFourForbiddenPlace(),
                    DoubleOpenThreeForbiddenPlace(),
                    OverlineForbiddenPlace()
                ),
                whiteForbiddenPlaces = listOf()
            )
        )
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

    @Test
    fun `검정 돌이 3-3인 경우에 놓으면 예외가 발생한다`() {
        board.place(Position(5, 5), Stone.BLACK)
        board.place(Position(8, 5), Stone.BLACK)
        board.place(Position(7, 7), Stone.BLACK)
        board.place(Position(7, 8), Stone.BLACK)

        assertThrows<IllegalArgumentException> { board.place(Position(7, 5), Stone.BLACK) }
    }

    @Test
    fun `검정 돌이 4-4인 경우에 놓으면 예외가 발생한다`() {
        board.place(Position(3, 9), Stone.BLACK)
        board.place(Position(6, 9), Stone.BLACK)
        board.place(Position(7, 9), Stone.BLACK)
        board.place(Position(9, 9), Stone.BLACK)

        assertThrows<IllegalArgumentException> { board.place(Position(5, 9), Stone.BLACK) }
    }

    @Test
    fun `검정 돌이 장목인 경우에 놓으면 예외가 발생한다`() {
        board.place(Position(0, 0), Stone.WHITE)
        board.place(Position(0, 7), Stone.WHITE)
        board.place(Position(0, 1), Stone.BLACK)
        board.place(Position(0, 2), Stone.BLACK)
        board.place(Position(0, 4), Stone.BLACK)
        board.place(Position(0, 5), Stone.BLACK)
        board.place(Position(0, 6), Stone.BLACK)

        assertThrows<IllegalArgumentException> { board.place(Position(0, 3), Stone.BLACK) }
    }

    @Test
    fun `하얀 돌은 4-4인 경우에 놓을 수 있다`() {
        board.place(Position(3, 9), Stone.WHITE)
        board.place(Position(6, 9), Stone.WHITE)
        board.place(Position(7, 9), Stone.WHITE)
        board.place(Position(9, 9), Stone.WHITE)

        assertDoesNotThrow { board.place(Position(5, 9), Stone.WHITE) }
    }

    @Test
    fun `하얀 돌은 장목인 경우에 놓을 수 있다`() {
        board.place(Position(0, 1), Stone.WHITE)
        board.place(Position(0, 2), Stone.WHITE)
        board.place(Position(0, 4), Stone.WHITE)
        board.place(Position(0, 5), Stone.WHITE)
        board.place(Position(0, 6), Stone.WHITE)

        assertDoesNotThrow { board.place(Position(0, 3), Stone.WHITE) }
    }
}
