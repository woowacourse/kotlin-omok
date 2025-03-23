package omok.model.player

import omok.model.player.state.PlayerState
import omok.model.player.state.WhitePlayerState

class WhitePlayer(
    playerState: PlayerState = WhitePlayerState(),
) : Player(playerState)
