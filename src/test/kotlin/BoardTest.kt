import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `보드에 돌을 놓는다`() {
        val stone = Stone(Point(1, 1), StoneColor.BLACK)
        val board = Board().testPlaceStone(stone)
        val actual = board.contains(stone)
        assertThat(actual).isTrue()
    }

    @Test
    fun `Stone이 좌표 범위 밖을 벗어났을 경우 예외를 표기한다`() {
        val point = Point(-1, -1)
        val stone = Stone(point, StoneColor.WHITE)
        val actual = Board().place(stone)
        assertThat(actual).isInstanceOf(Either.Left::class.java)
    }
}
