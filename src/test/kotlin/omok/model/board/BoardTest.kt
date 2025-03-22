package omok.model.board

import omok.fixture.generateTestBoardFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `원하는 크기의 Point 목록을 생성할 수 있다`() {
        val size = 15
        val board = Board(size = size)

        assertThat(board.points.size).isEqualTo(size * size)
    }

    @Test
    fun `바둑판의 크기가 15~25사이가 아니라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Board(size = 26) }
    }

    @Test
    fun `Point가 Open 상태일 때, 돌을 둘 수 있다`() {
        val board = Board()
        val position = Point(1, 1)
        board.placeStone(position, PointState.WHITE)

        val actual = board.findPoint(position)?.second

        assertThat(actual).isEqualTo(PointState.WHITE)
    }

    @Test
    fun `Point에 이미 돌이 있다면 돌을 둘 수 없다`() {
        val board = Board()
        val position = Point(1, 1)
        board.placeStone(position, PointState.WHITE)

        val actual = board.placeStone(position, PointState.BLACK)
        val expected = PlaceStoneResult.AlreadyPlaced

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드에서 원하는 좌표의 point를 찾을 수 있다`() {
        val board = Board()
        val position = Point(1, 1)

        val actual = board.findPoint(position)?.first

        assertThat(actual).isEqualTo(position)
    }

    @Test
    fun `흰돌은 착수 시 금수를 판단하지 않는다`() {
        val board =
            generateTestBoardFixture(
                listOf(Point(3, 12), Point(5, 12), Point(4, 14), Point(4, 13)),
                PointState.WHITE,
            )
        val point = Point(4, 12)

        val actual = board.placeStone(point, PointState.WHITE)
        val expected = PlaceStoneResult.Success(point)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `금수로 판단되면 Close를 반환한다`() {
        val board =
            generateTestBoardFixture(
                listOf(Point(1, 1), Point(2, 1), Point(3, 1), Point(4, 1), Point(6, 1)),
                PointState.BLACK,
            )
        val point = Point(5, 1)

        val actual = board.placeStone(point, PointState.BLACK)
        val expected = PlaceStoneResult.Closed

        assertThat(actual).isEqualTo(expected)
    }
}
