import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
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

    @Test
    fun `가로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board = Board()
        board.map[Point(1, 1)] = StoneColor.BLACK
        board.map[Point(2, 1)] = StoneColor.BLACK
        board.map[Point(3, 1)] = StoneColor.BLACK
        board.map[Point(4, 1)] = StoneColor.BLACK
        board.map[Point(5, 1)] = StoneColor.BLACK
        val actual = board.startCheckOmok(StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `왼쪽 위에서 오른쪽 아래 대각선으로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board = Board()
        board.map[Point(1, 1)] = StoneColor.BLACK
        board.map[Point(2, 2)] = StoneColor.BLACK
        board.map[Point(3, 3)] = StoneColor.BLACK
        board.map[Point(4, 4)] = StoneColor.BLACK
        board.map[Point(5, 5)] = StoneColor.BLACK
        val actual = board.startCheckOmok(StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `세로로 하얀 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board = Board()
        board.map[Point(1, 1)] = StoneColor.WHITE
        board.map[Point(1, 2)] = StoneColor.WHITE
        board.map[Point(1, 3)] = StoneColor.WHITE
        board.map[Point(1, 4)] = StoneColor.WHITE
        board.map[Point(1, 5)] = StoneColor.WHITE
        val actual = board.startCheckOmok(StoneColor.WHITE)
        assertThat(actual).isTrue()
    }
    @Test
    fun `세로로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board = Board()
        board.map[Point(1, 1)] = StoneColor.BLACK
        board.map[Point(1, 2)] = StoneColor.BLACK
        board.map[Point(1, 3)] = StoneColor.BLACK
        board.map[Point(1, 4)] = StoneColor.BLACK
        board.map[Point(1, 5)] = StoneColor.BLACK
        val actual = board.startCheckOmok(StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `오른쪽 위에서 왼쪽 아래 대각선으로 같은 색 돌이 5개 연속으로 있을 경우 오목이다`() {
        val board = Board()
        board.map[Point(5, 1)] = StoneColor.BLACK
        board.map[Point(4, 2)] = StoneColor.BLACK
        board.map[Point(3, 3)] = StoneColor.BLACK
        board.map[Point(2, 4)] = StoneColor.BLACK
        board.map[Point(1, 5)] = StoneColor.BLACK
        val actual = board.startCheckOmok(StoneColor.BLACK)
        assertThat(actual).isTrue()
    }

    @Test
    fun `tst`() {
        val board = Board()
        board.map[Point(2, 2)] = StoneColor.BLACK
        board.map[Point(1, 2)] = StoneColor.BLACK
        board.map[Point(2, 1)] = StoneColor.WHITE
        board.map[Point(1, 1)] = StoneColor.WHITE
        val actual = board.startCheckOmok(StoneColor.BLACK)
        assertThat(actual).isFalse()
    }
}

