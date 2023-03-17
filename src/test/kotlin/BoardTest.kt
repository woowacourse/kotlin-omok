import domain.board.Board
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `이미 돌이 놓여진 위치라면 true를 반환한다`() {
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3, Color.BLACK),
                Stone(1, 2, Color.WHITE),
                Stone(1, 4, Color.BLACK)
            )
        )
        val actual = board.isPlaced(Position(1, 3))
        println(actual)

        assertThat(actual).isTrue
    }

    @Test
    fun `돌이 놓여지지 않은 위치라면 false를 반환한다`() {
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3, Color.BLACK),
                Stone(1, 2, Color.WHITE),
                Stone(1, 4, Color.BLACK)
            )
        )
        val actual = board.isPlaced(Position(2, 5))

        assertThat(actual).isFalse
    }
}
