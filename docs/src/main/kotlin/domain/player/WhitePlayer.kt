package domain.player

import domain.rule.OmokRule
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones

class WhitePlayer(state: PlayerState = PlayingState()) : Player(state, StoneColor.WHITE) {
    override fun putStone(stone: Stone, otherStones: Stones, rule: OmokRule): Player =
        WhitePlayer(state.add(stone, rule))
}
