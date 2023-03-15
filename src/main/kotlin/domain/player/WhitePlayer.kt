package domain.player

import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone

class WhitePlayer(state: PlayerState = PlayingState()) : Player(state) {
    override fun putStone(stone: Stone): Player = WhitePlayer(state.add(stone))
}
