import domain.board.Board
import domain.player.Player
import domain.stone.Color
import domain.stone.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {

    @Test
    fun `플레이어가 아무것도 놓이지 않은 곳에 돌을 놓으려고 한다면 true를 반환한다`() {
        val player = Player()
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3, Color.BLACK),
                Stone(1, 2, Color.WHITE),
                Stone(1, 4, Color.BLACK)
            )
        )

        val actual = player.isPossibleToPlace(board, createPoint(1, 5))

        assertThat(actual).isTrue
    }

    @Test
    fun `플레이어가 돌이 이미 놓인 곳에 돌을 놓으려고 한다면 false를 반환한다`() {
        val player = Player()
        val board = Board(
            _placedStones = listOf(
                Stone(1, 3),
                Stone(1, 2),
                Stone(1, 4)
            )
        )

        val actual = player.isPossibleToPlace(board, createPoint(1, 3))

        assertThat(actual).isFalse
    }

    @Test
    fun `플레이어가 돌을 놓게 되면 놓은 돌을 반영한 board를 반환한다`() {
        val player = Player()
        val board = Board()

        val actual = player.placeStone(board, createPoint(1, 3))
        val expected = Board(
            _placedStones = listOf(Stone(1, 3))
        )

        assertThat(actual).isEqualTo(expected)
    }

    private fun Player.placeStone(currentBoard: Board, placingPoint: Point): Board {
        return this.placeStone(
            currentBoard = currentBoard,
            checkBoard = {},
            decidePoint = { placingPoint }
        )
    }

    private fun Player(): Player {

        return object : Player() {
            override val color: Color
                get() = Color.WHITE
        }
    }
}
