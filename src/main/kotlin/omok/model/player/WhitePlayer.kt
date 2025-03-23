package omok.model.player

import omok.model.player.state.PlayerState
import omok.model.player.state.WhitePlayerState
import omok.model.stone.PositionState

class WhitePlayer(
    playerState: PlayerState = WhitePlayerState(),
) : Player(playerState) {
    override val positionState: PositionState = PositionState.WHITE_POSITION
}
