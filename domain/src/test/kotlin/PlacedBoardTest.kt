import domain.board.PlacedBoard
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlacedBoardTest {
    @Test
    fun `이미 놓인 위치에 돌을 놓으면 자기 자신을 반환한다`() {
        val placedBoard = PlacedBoard().putStone(Stone(Position(1, 3), Color.BLACK))
        val nextBoard = placedBoard.putStone(Stone(Position(1, 3), Color.WHITE))
        assertThat(placedBoard).isEqualTo(nextBoard)
    }

    @Test
    fun `돌이 없는 곳에 돌을 놓을 수 있다`() {
        val placedBoard = PlacedBoard().putStone(Stone(Position(1, 3), Color.BLACK))
        val nextBoard = placedBoard.putStone(Stone(Position(2, 3), Color.WHITE))
        val nextBoardState =
            nextBoard.getBoards().filter { it.value != null }.map { Stone(it.key, it.value!!) }

        val expected = listOf(Stone(Position(1, 3), Color.BLACK), Stone(Position(2, 3), Color.WHITE))
        assertThat(nextBoardState).isEqualTo(expected)
    }
}
