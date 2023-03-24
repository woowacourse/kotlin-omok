package domain.player

import domain.rule.OmokRule
import domain.state.LoseState
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.Stones

class BlackPlayer(state: PlayerState = PlayingState()) : Player(state) {
    val isLose
        get() = state is LoseState

    override fun putStone(stone: Stone, otherStones: Stones, rule: OmokRule): Player {
        val blackStones = state.getAllStones()
        if (rule.check(blackStones, otherStones, stone)) {
            return BlackPlayer(LoseState(state.getAllStones()))
        }
        return BlackPlayer(state.add(stone, rule))
    }
}
