import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {

    @Test
    fun `플레이어가 아무것도 놓이지 않은 곳에 돌을 놓으려고 한다면 true를 반환한다`() {
        val player = createPlayer()
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3, Color.BLACK),
                Stone(1, 2, Color.WHITE),
                Stone(1, 4, Color.BLACK)
            )
        )

        val actual = player.isPossibleToPlace(board, Point(1, 5))

        assertThat(actual).isTrue
    }

    @Test
    fun `플레이어가 돌이 이미 놓인 곳에 돌을 놓으려고 한다면 false를 반환한다`() {
        val player = createPlayer()
        val board = Board(
            _placedStones = listOf(
                domain.stone.Stone(Point(1, 3), Color.BLACK),
                domain.stone.Stone(Point(1, 2), Color.WHITE),
                domain.stone.Stone(Point(1, 4), Color.BLACK)
            )
        )

        val actual = player.isPossibleToPlace(board, Point(1, 3))

        assertThat(actual).isFalse
    }
}
