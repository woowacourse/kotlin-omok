package omok.fixture

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState

fun generateTestBoardFixture(
    points: List<Point>,
    state: PointState,
): Board {
    return Board(points.associateWith { state })
}
