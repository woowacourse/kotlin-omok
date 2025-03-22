package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.rule.BlackStoneRule
import omok.domain.rule.WhiteStoneRule
import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class WhiteTurn(
    private val boardSize: Int = OmokBoard.DEFAULT_BOARD_SIZE,
    override val blackStones: Stones = Stones(BlackStoneRule(boardSize)),
    override val whiteStones: Stones = Stones(WhiteStoneRule(boardSize)),
) : Playing {
    override fun place(point: Point): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }

        val newStones = whiteStones + point
        return when {
            whiteStones.isOmok(point) -> Finished(blackStones, newStones, StoneColor.WHITE)
            blackStones.points.size + newStones.points.size >= boardSize * boardSize ->
                Finished(blackStones, newStones, null)

            else -> BlackTurn(boardSize, blackStones, newStones)
        }
    }

    override fun lastStonePoint(): Point? = blackStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.WHITE

    companion object {
        private const val ERROR_INVALID_POINT = "[ERROR] 이미 돌이 놓여져 있습니다."
    }
}
