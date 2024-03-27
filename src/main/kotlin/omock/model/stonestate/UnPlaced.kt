package omock.model.stonestate

import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.stone.Stone

sealed class UnPlaced(private val stone: Stone) : StoneState(stone) {
    override fun put(player: Player): StoneState {
        return when (player) {
            is BlackPlayer -> Black(stone)
            is WhitePlayer -> White(stone)
        }
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }
}
