package omok.model

import rule.BlackRenjuRule
import rule.OmokRule
import rule.wrapper.point.Point

class BlackPlayer : Player() {
    override val points = Points()
    override val rule: OmokRule = BlackRenjuRule()

    override fun checkGameState(newPoint: Point): GameState {
        return when (rule.checkSerialSameStonesBiDirection(points.points, newPoint, OMOK_CONDITION)) {
            true -> GameState.BLACK_OMOK
            false -> GameState.PLAYING
        }
    }

    companion object {
        private const val OMOK_CONDITION = 5
    }
}
