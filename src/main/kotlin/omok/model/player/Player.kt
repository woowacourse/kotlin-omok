package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.player.state.Finish
import omok.model.player.state.PlayerState
import omok.model.stone.StoneState

abstract class Player(
    private var playerState: PlayerState,
) {
    abstract val stoneState: StoneState

    fun isFinish(): Boolean = playerState is Finish

    fun put(
        position: Position,
        omokBoard: OmokBoard,
    ) {
        playerState = playerState.placeTurn(omokBoard, position, stoneState)
    }

    fun nextPlayer(): Player =
        when (this) {
            is BlackPlayer -> WhitePlayer(playerState)
            is WhitePlayer -> BlackPlayer(playerState)
            else -> this
        }
}
