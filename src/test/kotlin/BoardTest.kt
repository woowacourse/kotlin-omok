import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `돌을 놓을 수 있는지 없는지 판단한다`() {
        // given
        val whitePlayer = Player()
        val blackPlayer = Player()
        val board = Board(whitePlayer, blackPlayer)
        val position = Position(HorizontalAxis.H, 3)

        // when
        val expect = board.isPlaceable(position)

        // then
        assertThat(expect).isTrue
    }
}
