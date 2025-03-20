package omok.model

import rule.BlackRenjuRule
import rule.OmokRule
import rule.wrapper.point.Point

class BlackPlayer : Player() {
    override val points = mutableListOf<Point>()
    override val rule: OmokRule = BlackRenjuRule()

    override fun checkGameState(newPoint: Point): GameState {
        return when (rule.checkSerialSameStonesBiDirection(points, newPoint, 5)) {
            true -> GameState.BLACK_OMOK
            false -> GameState.PLAYING
        }
    }
}
