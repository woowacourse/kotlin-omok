package player

import BoardState
import Stone
import state.PlayerState
import state.PlayingState

class BlackPlayer(state: PlayerState = PlayingState()) : Player(state) {
    override val boardState: BoardState = BoardState.BLACK
    override fun putStone(stone: Stone): Player = BlackPlayer(state.add(stone))
}
