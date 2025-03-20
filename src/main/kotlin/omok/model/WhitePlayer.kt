package omok.model

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class WhitePlayer : Player() {
    override val points = Points()
    override val rule: OmokRule = WhiteRenjuRule()

    override fun checkGameState(newPoint: Point): GameState {
        return when (rule.checkSerialSameStonesBiDirection(points.points, newPoint, 5)) {
            true -> GameState.WHITE_OMOK
            false -> GameState.PLAYING
        }
    }
}
