package omok.domain.rule

import omok.domain.Board
import omok.domain.Point

class WhiteStoneRule(
    boardSize: Int = Board.DEFAULT_BOARD_SIZE,
) : OmokRule(boardSize) {
    override fun isFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean = false
}
