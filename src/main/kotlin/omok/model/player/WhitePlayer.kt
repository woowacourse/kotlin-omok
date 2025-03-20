package omok.model.player

import omok.model.player.state.PlayerState
import omok.model.player.state.WhitePlayerState
import omok.model.stone.StoneState

class WhitePlayer(
    playerState: PlayerState = WhitePlayerState(),
) : Player(playerState) {
    override val stoneState: StoneState = StoneState.WHITE
}
