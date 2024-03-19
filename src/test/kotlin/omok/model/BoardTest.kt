package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `오목판에 돌을 놓는다`() {
        // given
        val board = Board()

        // when
        board.place(Position(3, 3), Stone.BLACK)

        // then
        val actual = board.find(Position(3, 3))
        assertThat(actual).isEqualTo(Stone.BLACK)
    }

    @Test
    fun `오목판에 이미 돌이 있는 곳에 놓으면 예외가 발생한다`() {
        // given
        val board = Board()

        // when
        board.place(Position(3, 3), Stone.BLACK)

        // then
        assertThrows<IllegalArgumentException> { board.place(Position(3, 3), Stone.BLACK) }
    }
}
