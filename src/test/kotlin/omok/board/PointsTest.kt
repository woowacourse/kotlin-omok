package omok.board

import omok.stone.Position
import omok.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class PointsTest {
    @Test
    fun `15 x 15 크기의 Point 목록을 생성할 수 있다`() {
        val points = Points.create()

        assertThat(points.points.size).isEqualTo(225)
    }

    @Test
    fun `Point가 Open 상태일 때, 돌을 둘 수 있다`() {
        val points = Points.create()
        points.placeStone(Position(1, 1), StoneColor.WHITE)

        val actual = points.points.find { it.position == Position(1, 1) }

        assertThat(actual?.state).isEqualTo(PointState.WHITE)
    }

    @Test
    fun `Point가 Open 상태가 아니면, 돌을 둘 수 없다`() {
        val points = Points.create()
        val position = Position(1, 1)
        points.placeStone(position, StoneColor.WHITE)

        assertThrows<IllegalArgumentException> {
            points.placeStone(position, StoneColor.BLACK)
        }
    }
}
