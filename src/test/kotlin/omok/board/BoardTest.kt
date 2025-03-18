package omok.board

import omok.stone.Position
import omok.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `보드에 돌을 둘 수 있다`() {
        val position = Position(1, 1)
        val color = StoneColor.WHITE
        val board = Board()

        board.placeStone(position, color)

        val actual = board.points.points.find { it.position == Position(1, 1) }

        assertThat(actual?.state).isEqualTo(PointState.WHITE)
    }
}
