package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones

class PlayingState(stones: Stones = Stones()) : PlayerState(stones) {
    override fun add(newStone: Stone, otherStones: Stones, rule: OmokRule, turn: StoneColor): PlayerState {
        val newStones = stones.add(newStone)
        val currentWin = newStones.checkWin(newStone)

        when (turn) {
            StoneColor.BLACK -> if (rule.check(blackStones = newStones, whiteStones = otherStones, turn, currentWin)) return LoseState(newStones)
            StoneColor.WHITE -> if (rule.check(blackStones = otherStones, whiteStones = newStones, turn, currentWin)) return LoseState(newStones)
        }
        if (currentWin) return WinState(newStones)
        return PlayingState(newStones)
    }
}
