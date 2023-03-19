package domain.player

import domain.rule.OmokRule
import domain.state.FoulState
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones

class BlackPlayer(state: PlayerState = PlayingState()) : Player(state, StoneColor.BLACK) {
    override fun putStone(stone: Stone, otherStones: Stones, rule: OmokRule): Player {
        val blackStones = state.getAllStones()
        if (rule.check(blackStones, otherStones, stone)) {
            return BlackPlayer(FoulState(blackStones))
        }
        return BlackPlayer(state.add(stone, rule))
    }
}
