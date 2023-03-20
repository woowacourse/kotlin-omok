import domain.OmokGameState
import domain.board.Board
import domain.stone.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameStateTest {

    @Test
    fun `같은 색깔의 돌이 5개 연속되면 게임이 끝난다`() {

        val board = Board(
            _placedStones = listOf(
                Stone(1, 1, Color.BLACK),
                Stone(1, 2, Color.BLACK),
                Stone(1, 3, Color.BLACK),
                Stone(1, 4, Color.BLACK),
                Stone(1, 5, Color.BLACK)
            )
        )

        val actual = OmokGameState.valueOf(board, Color.BLACK)
        val expected = OmokGameState.End(Color.BLACK)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은 색깔의 돌이 5개 이상 연속되지 않으면 게임은 계속 진행된다`() {

        val board = Board(
            _placedStones = listOf(
                Stone(1, 1, Color.BLACK),
                Stone(1, 2, Color.BLACK),
                Stone(1, 3, Color.BLACK),
                Stone(1, 4, Color.BLACK),
                Stone(1, 5, Color.BLACK)
            )
        )

        val actual = OmokGameState.valueOf(board, Color.BLACK)
        val expected = OmokGameState.Running

        assertThat(actual).isEqualTo(expected)
    }
}
