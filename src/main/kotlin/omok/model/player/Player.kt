package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.player.state.BlackPlayerState
import omok.model.player.state.GameState
import omok.model.player.state.PlayerState
import omok.model.player.state.WhitePlayerState

class Player {
    var playerState: PlayerState = BlackPlayerState()
        private set

    private var gameState: GameState = GameState.Playing

    fun win(): Boolean = gameState is GameState.Win

    fun playing(): Boolean = gameState is GameState.Playing

    fun put(
        position: Position,
        omokBoard: OmokBoard,
    ) {
        gameState = playerState.placeTurn(omokBoard, position)
    }

    fun nextPlayer() {
        if (gameState == GameState.ForbiddenMove) return
        if (gameState == GameState.Playing) {
            playerState =
                when (playerState) {
                    is BlackPlayerState -> WhitePlayerState()
                    is WhitePlayerState -> BlackPlayerState()
                    else -> throw IllegalStateException("존재하지 않는 플레이어입니다.")
                }
        }
    }
}
