import domain.model.Board
import domain.model.Column
import domain.model.Position
import domain.model.Row
import domain.model.Stone
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `바둑돌을 둔다`() {
        val position = Position(Column.from('A'), Row(1))
        board = board.placeStone(position, Stone.BLACK)
        assertThat(board.stones[position]).isEqualTo(Stone.BLACK)
    }

    @Test
    fun `바둑돌이 이미 존재하는 위치는 둘 수 없다`() {
        val position = Position(Column.from('A'), Row(1))
        board = board.placeStone(position, Stone.BLACK)
        assertThatThrownBy {
            board.placeStone(
                position,
                Stone.BLACK,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이미 있는 곳에는 둘 수 없습니다.")
    }
}
