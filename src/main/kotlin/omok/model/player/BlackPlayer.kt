package omok.model.player

import omok.model.player.state.BlackPlayerState
import omok.model.player.state.PlayerState

class BlackPlayer(
    playerState: PlayerState = BlackPlayerState(),
) : Player(playerState)
