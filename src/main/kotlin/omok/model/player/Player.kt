package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.player.state.PlayerState
import omok.model.player.state.Win

abstract class Player(
    private var playerState: PlayerState,
) {
    fun win(): Boolean = playerState is Win

    fun put(
        position: Position,
        omokBoard: OmokBoard,
    ) {
        playerState = playerState.placeTurn(omokBoard, position)
    }

    fun nextPlayer(): Player {
//        if (playerState is BlackPlayerState) return this
        return when (this) {
            is BlackPlayer -> WhitePlayer(playerState)
            is WhitePlayer -> BlackPlayer(playerState)
            else -> throw IllegalStateException("존재하지 않는 플레이어입니다.")
        }
    }
}
