package omock.model.stonestate

import omock.model.stone.Stone
import omock.model.stonestate.UnPlaced

class Clear(private val stone: Stone) : UnPlaced(stone)
