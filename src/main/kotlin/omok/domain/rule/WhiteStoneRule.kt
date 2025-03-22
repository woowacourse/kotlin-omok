package omok.domain.rule

import omok.domain.OmokBoard
import omok.domain.Point

class WhiteStoneRule(
    boardSize: Int = OmokBoard.DEFAULT_BOARD_SIZE,
) : OmokRule(boardSize) {
    override fun isFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean = false
}
