package omock.model.stonestate.unplaced

import omock.model.stone.Stone
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.stonestate.placed.Black
import omock.model.stonestate.StoneState
import omock.model.stonestate.placed.White

abstract class UnPlaced(private val stone: Stone) : StoneState {
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
