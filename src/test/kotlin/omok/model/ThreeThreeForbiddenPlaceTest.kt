package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * docs/3-3 금수 테스트 케이스.png 참조
 */
class ThreeThreeForbiddenPlaceTest {
    @Test
    fun `3-3 금수 테스트 케이스 A의 경우 돌을 놓을 수 없다`() {
        // given
        val board = Board()
        val forbiddenPlace = ThreeThreeForbiddenPlace()

        // when
        board.place(Position(1, 3), Stone.BLACK)
        board.place(Position(2, 3), Stone.BLACK)
        board.place(Position(3, 2), Stone.BLACK)
        board.place(Position(3, 4), Stone.BLACK)

        val actual = forbiddenPlace.availablePosition(board, Position(3, 3), Stone.BLACK)
        assertThat(actual).isFalse
    }

    @Test
    fun `3-3 금수 테스트 케이스 B의 경우 돌을 놓을 수 없다`() {
        // given
        val board = Board()
        val forbiddenPlace = ThreeThreeForbiddenPlace()

        // when
        board.place(Position(10, 1), Stone.BLACK)
        board.place(Position(11, 2), Stone.BLACK)
        board.place(Position(10, 4), Stone.BLACK)
        board.place(Position(11, 4), Stone.BLACK)

        val actual = forbiddenPlace.availablePosition(board, Position(13, 4), Stone.BLACK)
        assertThat(actual).isFalse
    }

    @Test
    fun `3-3 금수 테스트 케이스 D의 경우 돌을 놓을 수 없다`() {
        // given
        val board = Board()
        val forbiddenPlace = ThreeThreeForbiddenPlace()

        // when
        board.place(Position(5, 5), Stone.BLACK)
        board.place(Position(8, 5), Stone.BLACK)
        board.place(Position(7, 7), Stone.BLACK)
        board.place(Position(7, 8), Stone.BLACK)

        val actual = forbiddenPlace.availablePosition(board, Position(7, 5), Stone.BLACK)
        assertThat(actual).isFalse
    }

    @Test
    fun `3-3 금수 테스트 케이스 C의 경우 돌을 놓을 수 없다`() {
        // given
        val board = Board()
        val forbiddenPlace = ThreeThreeForbiddenPlace()

        // when
        board.place(Position(0, 4), Stone.BLACK)
        board.place(Position(3, 1), Stone.BLACK)
        board.place(Position(2, 4), Stone.BLACK)
        board.place(Position(3, 5), Stone.BLACK)

        val actual = forbiddenPlace.availablePosition(board, Position(1, 3), Stone.BLACK)
        assertThat(actual).isFalse
    }
}
