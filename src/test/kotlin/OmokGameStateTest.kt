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
}
