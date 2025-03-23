package omok.fixture

import omok.model.board.Board
import omok.model.board.BoardPoints
import omok.model.board.BoardSize
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.OmokRuleJudge
import omok.model.rule.count.FiveInRowRule

fun generateTestBoardFixture(
    points: List<Point>,
    state: PointState,
): Board {
    val judge =
        OmokRuleJudge().apply {
            applyRenjuRule()
            applyWinningRule(FiveInRowRule())
        }
    val boardPoints = BoardPoints(BoardSize.DEFAULT, points.associateWith { state })
    return Board(boardPoints, judge)
}
