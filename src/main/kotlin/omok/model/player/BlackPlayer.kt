package omok.model.player

import omok.model.player.state.BlackPlayerState
import omok.model.player.state.PlayerState
import omok.model.stone.StoneColor
import omok.model.stone.StoneState

class BlackPlayer(
    private val playerState: PlayerState = BlackPlayerState(),
    private val stoneState: StoneState,
) : Player(playerState, stoneState) {
    override val stoneColor: StoneColor = StoneColor.BLACK
}
