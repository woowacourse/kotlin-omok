import domain.board.Board
import domain.player.PlacingPoint
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlacingPointTest {

    @Test
    fun `33에 해당하는 수를 놓으면 FORBIDDEN을 반환한다`() {
        val stones = listOf(
            Stone(3, 12, Color.BLACK),
            Stone(4, 13, Color.BLACK),
            Stone(4, 14, Color.BLACK),
            Stone(5, 12, Color.BLACK),
        )
        val actual = PlacingPoint.valueOf(Board(stones), Point(4, 12))
        assertThat(actual).isEqualTo(PlacingPoint.FORBIDDEN)
    }

    @Test
    fun `44에 해당하는 수를 놓으면 FORBIDDEN을 반환한다`() {
        val stones = listOf(
            Stone(6, 5, Color.BLACK),
            Stone(8, 6, Color.BLACK),
            Stone(8, 7, Color.BLACK),
            Stone(8, 8, Color.BLACK),
            Stone(10, 8, Color.BLACK),
            Stone(10, 9, Color.BLACK),
            Stone(11, 8, Color.BLACK),
        )
        val actual = PlacingPoint.valueOf(Board(stones), Point(9, 8))
        assertThat(actual).isEqualTo(PlacingPoint.FORBIDDEN)
    }
}
fun Stone(x: Int, y: Int, color: Color): Stone = Stone(Point(x, y), color)
