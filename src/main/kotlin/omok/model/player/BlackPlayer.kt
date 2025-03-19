package omok.model.player

import omok.model.stone.StoneColor
import omok.model.stone.StoneState

class BlackPlayer(
    private val stoneState: StoneState,
) : Player(stoneState) {
    override val stoneColor: StoneColor = StoneColor.BLACK
}
