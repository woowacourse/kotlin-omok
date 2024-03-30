import omok.model.Board
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
}
