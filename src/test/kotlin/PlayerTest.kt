import domain.board.Board
import domain.player.Player
import domain.stone.Color
import domain.stone.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {

    @Test
    fun `돌을 놓는 것을 성공한다면 새로운 돌이 놓여진 board를 반환한다`() {
        val board = Board()
        val player = Player(Color.WHITE)

        val actual = player.putStone(board) { Position(1, 1) }
        val expected = Board(
            _placedStones = listOf(Stone(1, 3, Color.WHITE))
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌이 놓는 것을 실패한다면 null을 반환한다`() {
        val board = Board(
            _placedStones = listOf(Stone(1, 1, Color.BLACK))
        )
        val player = Player(Color.WHITE)

        val actual = player.putStone(board) { Position(1, 1) }

        assertThat(actual).isEqualTo(null)
    }
}
