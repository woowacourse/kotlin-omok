package domain.player

import domain.rule.RenjuRule
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.Stones

class BlackPlayer(state: PlayerState = PlayingState()) : Player(state) {
    override fun putStone(stone: Stone, otherStones: Stones): Player {
        val blackStones = state.getAllStones()
        if (!RenjuRule().check(blackStones, otherStones, stone)) {
            return BlackPlayer(state.add(stone))
        } else {
            throw IllegalStateException("3-3입니다!")
        }
    }
}
