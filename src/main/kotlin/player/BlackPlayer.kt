package player

import Stone
import state.PlayerState
import state.PlayingState

class BlackPlayer(state: PlayerState = PlayingState()) : Player(state) {
    override fun putStone(stone: Stone): Player = BlackPlayer(state.add(stone))
}
