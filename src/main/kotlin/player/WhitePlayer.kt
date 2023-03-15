package player

import BoardState
import Stone
import state.PlayerState
import state.PlayingState

class WhitePlayer(state: PlayerState = PlayingState()) : Player(state) {
    override val boardState: BoardState = BoardState.WHITE
    override fun putStone(stone: Stone): Player = WhitePlayer(state.add(stone))
}
