package omok.model

import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * docs/3-3 금수 테스트 케이스.png 참조
 */
class DoubleOpenThreeForbiddenPlaceTest {
    private val forbiddenPlace = DoubleOpenThreeForbiddenPlace()

    @Test
    fun `3-3 금수 테스트 케이스 A의 경우 돌을 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(1, 3), Stone.BLACK),
            StonePosition(Position(2, 3), Stone.BLACK),
            StonePosition(Position(3, 2), Stone.BLACK),
            StonePosition(Position(3, 4), Stone.BLACK),
        )

        val actual = forbiddenPlace.availablePosition(board, Position(3, 3))
        assertThat(actual).isFalse
    }

    @Test
    fun `3-3 금수 테스트 케이스 B의 경우 돌을 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(9, 1), Stone.BLACK),
            StonePosition(Position(10, 2), Stone.BLACK),
            StonePosition(Position(9, 4), Stone.BLACK),
            StonePosition(Position(10, 4), Stone.BLACK),
        )

        val actual = forbiddenPlace.availablePosition(board, Position(12, 4))
        assertThat(actual).isFalse
    }

    @Test
    fun `3-3 금수 테스트 케이스 C의 경우 돌을 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(3, 12), Stone.BLACK),
            StonePosition(Position(5, 12), Stone.BLACK),
            StonePosition(Position(6, 9), Stone.BLACK),
            StonePosition(Position(6, 13), Stone.BLACK),
        )

        val actual = forbiddenPlace.availablePosition(board, Position(4, 11))
        assertThat(actual).isFalse
    }

    @Test
    fun `3-3 금수 테스트 케이스 D의 경우 돌을 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(5, 5), Stone.BLACK),
            StonePosition(Position(8, 5), Stone.BLACK),
            StonePosition(Position(7, 7), Stone.BLACK),
            StonePosition(Position(7, 8), Stone.BLACK),
        )

        val actual = forbiddenPlace.availablePosition(board, Position(7, 5))
        assertThat(actual).isFalse
    }
}
