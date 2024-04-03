package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.DoubleFourPlace
import woowacourse.omok.model.rule.ban.DoubleFourForbiddenPlace

/**
 * docs/4-4 금수 테스트 케이스.png 참조
 */
class DoubleFourForbiddenPlaceTest {
    private val forbiddenPlace = DoubleFourForbiddenPlace()

    @Test
    fun `4-4 금수 테스트 케이스 A의 경우 돌을 놓을 수 없다`() {
        val board =
            initBoard(
                StonePosition(Position(3, 2), Stone.BLACK),
                StonePosition(Position(3, 3), Stone.BLACK),
                StonePosition(Position(3, 6), Stone.BLACK),
                StonePosition(Position(3, 8), Stone.BLACK),
                StonePosition(Position(3, 9), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(3, 5), Stone.BLACK)
        assertThat(actual).isInstanceOf(DoubleFourPlace::class.java)
    }

    @Test
    fun `4-4 금수 테스트 케이스 B의 경우 돌을 놓을 수 없다`() {
        val board =
            initBoard(
                StonePosition(Position(3, 9), Stone.BLACK),
                StonePosition(Position(6, 9), Stone.BLACK),
                StonePosition(Position(7, 9), Stone.BLACK),
                StonePosition(Position(9, 9), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(5, 9), Stone.BLACK)
        assertThat(actual).isInstanceOf(DoubleFourPlace::class.java)
    }

    @Test
    fun `4-4 금수 테스트 케이스 C의 경우 돌을 놓을 수 없다`() {
        val board =
            initBoard(
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(1, 2), Stone.BLACK),
                StonePosition(Position(3, 2), Stone.BLACK),
                StonePosition(Position(4, 2), Stone.BLACK),
                StonePosition(Position(5, 2), Stone.BLACK),
                StonePosition(Position(9, 4), Stone.BLACK),
                StonePosition(Position(10, 5), Stone.BLACK),
                StonePosition(Position(11, 6), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(7, 2), Stone.BLACK)
        assertThat(actual).isInstanceOf(DoubleFourPlace::class.java)
    }

    @Test
    fun `4-4 금수 테스트 케이스 D의 경우 돌을 놓을 수 없다`() {
        val board =
            initBoard(
                StonePosition(Position(5, 9), Stone.BLACK),
                StonePosition(Position(6, 9), Stone.BLACK),
                StonePosition(Position(6, 10), Stone.BLACK),
                StonePosition(Position(6, 7), Stone.BLACK),
                StonePosition(Position(7, 7), Stone.BLACK),
                StonePosition(Position(9, 5), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(6, 8), Stone.BLACK)
        assertThat(actual).isInstanceOf(DoubleFourPlace::class.java)
    }

    @Test
    fun `4-4 금수 테스트 케이스 E의 경우 돌을 놓을 수 없다`() {
        val board =
            initBoard(
                StonePosition(Position(7, 7), Stone.BLACK),
                StonePosition(Position(8, 7), Stone.BLACK),
                StonePosition(Position(9, 7), Stone.BLACK),
                StonePosition(Position(10, 6), Stone.BLACK),
                StonePosition(Position(10, 5), Stone.BLACK),
                StonePosition(Position(10, 4), Stone.BLACK),
                StonePosition(Position(6, 7), Stone.WHITE),
                StonePosition(Position(10, 3), Stone.WHITE),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(10, 7), Stone.WHITE)
        assertThat(actual).isInstanceOf(DoubleFourPlace::class.java)
    }
}
