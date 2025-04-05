package omok.model.board

import omok.fixture.overlineForbiddenBoard
import omok.fixture.rules
import omok.fixture.whitePassForbiddenMoveBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PlaceStoneResult
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.PointState

class BoardTest {
    lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(BoardSize(15), rules)
    }

    @Test
    fun `15 x 15 크기의 Point 목록을 생성할 수 있다`() {
        assertThat(board.points.size).isEqualTo(225)
    }

    @Test
    fun `Point가 Open 상태일 때, 돌을 둘 수 있다`() {
        val point = Point(1, 1)
        board.placeStone(point, StoneColor.WHITE)

        val actual = board.findPoint(point)

        assertThat(actual.state).isEqualTo(PointState.WHITE)
    }

    @Test
    fun `Point에 이미 돌이 있다면 돌을 둘 수 없다`() {
        val point = Point(1, 1)
        board.placeStone(point, StoneColor.WHITE)

        val actual = board.placeStone(point, StoneColor.BLACK)
        val expected = PlaceStoneResult.AlreadyPlaced

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드에서 원하는 좌표의 point를 찾을 수 있다`() {
        val point = Point(1, 1)

        val actual = board.findPoint(point)

        assertThat(actual).isEqualTo(point)
    }

    @Test
    fun `흰돌은 착수 시 금수를 판단하지 않는다`() {
        val board = whitePassForbiddenMoveBoard
        val point = Point(4, 12)

        val actual = board.placeStone(point, StoneColor.WHITE)
        val expected = PlaceStoneResult.Success(point)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `금수로 판단되면 ForbiddenMove를 반환한다`() {
        val board = overlineForbiddenBoard
        val point = Point(5, 1)

        val actual = board.placeStone(point, StoneColor.BLACK)
        val expected = PlaceStoneResult.ForbiddenMove

        assertThat(actual).isEqualTo(expected)
    }
}
