package domain.player

import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.Stone

class BlackPlayer(state: PlayerState = PlayingState()) : Player(state) {
    override fun putStone(stone: Stone): Player = BlackPlayer(state.add(stone))
}
