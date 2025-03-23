package omok.domain.rule

import omok.domain.OmokGame
import omok.domain.Point

class WhiteStoneRule(
    boardSize: Int = OmokGame.DEFAULT_BOARD_SIZE,
) : OmokRule(boardSize) {
    override fun isFoul(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
        startPoint: Point,
    ): Boolean = false
}
