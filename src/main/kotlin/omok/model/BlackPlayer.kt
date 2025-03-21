package omok.model

import omok.model.adapter.BlackRuleAdapter
import omok.model.adapter.OmokRuleAdapter

class BlackPlayer : Player() {
    override val points = Points()
    override val omokRuleAdapter: OmokRuleAdapter = BlackRuleAdapter()

    override fun checkGameState(newPoint: Point): GameState {
        return when (omokRuleAdapter.checkSerialSameStonesBiDirection(points.points, newPoint, OMOK_CONDITION)) {
            true -> GameState.BLACK_OMOK
            false -> GameState.PLAYING
        }
    }

    companion object {
        private const val OMOK_CONDITION = 5
    }
}
