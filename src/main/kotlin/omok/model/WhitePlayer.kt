package omok.model

import omok.model.adapter.OmokRuleAdapter
import omok.model.adapter.WhiteRuleAdapter


class WhitePlayer : Player() {
    override val points = Points()
    override val omokRuleAdapter: OmokRuleAdapter = WhiteRuleAdapter()

    override fun checkGameState(newPoint: Point): GameState {
        return when (omokRuleAdapter.checkSerialSameStonesBiDirection(points.points, newPoint, OMOK_CONDITION)) {
            true -> GameState.WHITE_OMOK
            false -> GameState.PLAYING
        }
    }

    companion object {
        private const val OMOK_CONDITION = 5
    }
}
