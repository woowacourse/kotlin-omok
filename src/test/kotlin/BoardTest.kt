import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.turn.BlackTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `보드에 돌을 놓는다`() {
        val point = Point(1, 2)
        val stone = Stone(point, StoneColor.BLACK)
        val board = Board()
        board.place(stone)
        val actual = board.contains(stone)
        assertThat(actual).isTrue()
    }

    @Test
    fun `Stone이 좌표 범위 밖을 벗어났을 경우 예외를 표기한다`() {
        val point = Point(-1, -1)
        val board = Board()
        val stone = Stone(point, StoneColor.WHITE)
        val actual = assertThrows<IllegalArgumentException> { board.place(stone) }.message
        assertThat(actual).isEqualTo("보드 밖에 돌을 두었습니다.돌을 놓은 곳 : ${stone.point.x} ${stone.point.y}")
    }

    @Test
    fun `특정 좌표에 흑돌을 놓는 기능`() {
        val board = Board()
        val point = Point(1, 1)
        val stone = Stone(point, StoneColor.BLACK)
        board.place(stone)
    }

    @Test
    fun `보드가 꽉 찼을때`() {
        val board = Board()
        val blackTurn = BlackTurn(board)
        blackTurn.placeStone(Point(1, 1))
    }
}
