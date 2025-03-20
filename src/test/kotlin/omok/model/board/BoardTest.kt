package omok.model.board

import omok.model.stone.Position
import omok.model.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `15 x 15 크기의 Point 목록을 생성할 수 있다`() {
        val board = Board()

        assertThat(board.points.size).isEqualTo(225)
    }

    @Test
    fun `Point가 Open 상태일 때, 돌을 둘 수 있다`() {
        val board = Board()
        board.placeStone(Position(1, 1), StoneColor.WHITE)

        val actual = board.points.find { it.position == Position(1, 1) }

        assertThat(actual?.state).isEqualTo(PointState.WHITE)
    }

    @Test
    fun `Point에 이미 돌이 있다면 돌을 둘 수 없다`() {
        val board = Board()
        val position = Position(1, 1)
        board.placeStone(position, StoneColor.WHITE)

        val actual = board.placeStone(position, StoneColor.BLACK)
        val expected = PlaceStoneResult.AlreadyPlaced

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드에서 원하는 좌표의 point를 찾을 수 있다`() {
        val board = Board()
        val position = Position(1, 1)

        val actual = board.findPoint(position)

        assertThat(actual?.position).isEqualTo(position)
    }
}
