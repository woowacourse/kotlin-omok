package omok.domain.state

import omok.domain.OmokGame
import omok.domain.Point
import omok.domain.rule.BlackStoneRule
import omok.domain.rule.WhiteStoneRule
import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class BlackTurn(
    private val boardSize: Int = OmokGame.DEFAULT_BOARD_SIZE,
    override val blackStones: Stones = Stones(BlackStoneRule(boardSize)),
    override val whiteStones: Stones = Stones(WhiteStoneRule(boardSize)),
) : Playing {
    override fun place(point: Point): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }
        require(!blackStones.isFoul(whiteStones, point)) { ERROR_RENJU_RULE }

        val newStones = blackStones + point
        return when {
            blackStones.isOmok(point) -> Finished(newStones, whiteStones, StoneColor.BLACK)
            newStones.points.size + whiteStones.points.size >= boardSize * boardSize ->
                Finished(newStones, whiteStones, null)

            else -> WhiteTurn(boardSize, newStones, whiteStones)
        }
    }

    override fun lastStonePoint(): Point? = whiteStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK

    companion object {
        private const val ERROR_INVALID_POINT = "[ERROR] 이미 돌이 놓여져 있습니다."
        private const val ERROR_RENJU_RULE = "[ERROR] 돌을 놓을 수 없습니다."
    }
}
