package omok.model.board

import omok.fixture.overlineForbiddenBoard
import omok.fixture.whitePassForbiddenMoveBoard
import omok.model.StoneColor
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
        val position = Position(1, 1)
        board.placeStone(position, StoneColor.WHITE)

        val actual = board.findPoint(position)

        assertThat(actual.state).isEqualTo(PointState.WHITE)
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

        assertThat(actual.position).isEqualTo(position)
    }

    @Test
    fun `흰돌은 착수 시 금수를 판단하지 않는다`() {
        val board = whitePassForbiddenMoveBoard
        val position = Position(4, 12)

        val actual = board.placeStone(position, StoneColor.WHITE)
        val expected = PlaceStoneResult.Success(Point(position))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `금수로 판단되면 Close를 반환한다`() {
        val board = overlineForbiddenBoard
        val position = Position(5, 1)

        val actual = board.placeStone(position, StoneColor.BLACK)
        val expected = PlaceStoneResult.Closed

        assertThat(actual).isEqualTo(expected)
    }
}
