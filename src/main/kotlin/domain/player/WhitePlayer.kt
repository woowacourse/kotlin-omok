package domain.player

import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone
import domain.stone.Stones

class WhitePlayer(state: PlayerState = PlayingState()) : Player(state) {
    override fun putStone(stone: Stone, otherStones: Stones): Player = WhitePlayer(state.add(stone))
}
