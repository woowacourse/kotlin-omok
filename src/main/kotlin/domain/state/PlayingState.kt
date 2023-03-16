package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.Stones

class PlayingState(stones: Stones = Stones()) : PlayerState(stones) {
    override fun add(newStone: Stone, rule: OmokRule): PlayerState {
        val newStones = stones.add(newStone)
        if (newStones.checkWin(newStone)) return WinState(newStones)
        return PlayingState(newStones)
    }
}
