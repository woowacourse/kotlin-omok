package omok.model.player

import omok.model.player.state.BlackPlayerState
import omok.model.player.state.PlayerState
import omok.model.stone.PositionState

class BlackPlayer(
    playerState: PlayerState = BlackPlayerState(),
) : Player(playerState) {
    override val positionState: PositionState = PositionState.BLACK_POSITION
}
