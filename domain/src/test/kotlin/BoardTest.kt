import domain.stone.Color
import domain.stone.Position
import domain.turn.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `이미 놓여진 위치라면 참을 반환한다`() {
        val stones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(4, 13, Color.WHITE),
            Stone(4, 14, Color.BLACK),
            Stone(5, 12, Color.WHITE),
        )
        val board = Board(stones.convertToBoard())
        val result = board.isAlreadyPut(Position(4, 13))
        assertThat(result).isTrue
    }

    @Test
    fun `추가한 돌을 포함한 새로운 보드가 생성된다`() {
        val stones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(4, 13, Color.WHITE),
            Stone(4, 14, Color.BLACK),
            Stone(5, 12, Color.WHITE),
        )
        val board = Board(stones.convertToBoard())
        val newStone = Stone(5, 13, Color.BLACK)
        val result = board.putStone(newStone)
        val expected = (stones + newStone).convertToBoard()
        assertThat(result.map).isEqualTo(expected)
    }
}
