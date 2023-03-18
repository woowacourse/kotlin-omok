import domain.board.Board
import domain.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `이미 돌이 놓여진 위치라면 true를 반환한다`() {
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3),
                Stone(1, 2),
                Stone(1, 4)
            )
        )
        val actual = board.isPlaced(createPoint(1, 3))

        assertThat(actual).isTrue
    }

    @Test
    fun `돌이 놓여지지 않은 위치라면 false를 반환한다`() {
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3),
                Stone(1, 2),
                Stone(1, 4)
            )
        )
        val actual = board.isPlaced(createPoint(1, 5))

        assertThat(actual).isFalse
    }
}
