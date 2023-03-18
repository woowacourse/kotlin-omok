import domain.board.Board
import domain.player.PlacingPoint
import domain.stone.Color
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
        val actual = PlacingPoint.valueOf(Board(stones), createPoint(4, 12))
        val expected = PlacingPoint.FORBIDDEN

        assertThat(actual).isEqualTo(expected)
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
        val actual = PlacingPoint.valueOf(Board(stones), createPoint(9, 8))
        val expected = PlacingPoint.FORBIDDEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `아무 규칙에 위반되지 않은 곳에 돌을 놓으려고 한다면 true를 반환한다`() {
        val stones = listOf(
            Stone(6, 5, Color.BLACK),
            Stone(8, 6, Color.BLACK),
            Stone(8, 7, Color.BLACK),
            Stone(8, 8, Color.BLACK),
            Stone(10, 8, Color.BLACK),
            Stone(10, 9, Color.BLACK),
            Stone(11, 8, Color.BLACK),
        )

        val actual = PlacingPoint.valueOf(Board(stones), createPoint(2, 1))
        val expected = PlacingPoint.ALLOWED

        assertThat(actual).isEqualTo(expected)
    }
}
