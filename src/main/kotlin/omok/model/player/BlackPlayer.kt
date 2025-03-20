package omok.model.player

import omok.model.player.state.BlackPlayerState
import omok.model.player.state.PlayerState
import omok.model.stone.StoneState

class BlackPlayer(
    playerState: PlayerState = BlackPlayerState(),
) : Player(playerState) {
    override val stoneState: StoneState = StoneState.BLACK
}
