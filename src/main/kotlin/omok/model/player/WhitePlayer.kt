package omok.model.player

import omok.model.player.state.PlayerState
import omok.model.player.state.WhitePlayerState
import omok.model.stone.StoneColor
import omok.model.stone.StoneState

class WhitePlayer(
    private val playerState: PlayerState = WhitePlayerState(),
    private val stoneState: StoneState,
) : Player(playerState, stoneState) {
    override val stoneColor: StoneColor = StoneColor.WHITE
}
