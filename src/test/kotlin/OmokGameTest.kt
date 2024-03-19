import omok.Board
import omok.Point
import omok.StoneColor
import omok.turn.BlackTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `흑 턴일때 돌을 놓으면 흑돌이다`() {
        val board = Board()
        val blackTurn = BlackTurn(board)
        val point = Point(1, 1)
        blackTurn.placeStone(point)
        assertThat(board.map[point]).isEqualTo(StoneColor.BLACK)
    }
}
