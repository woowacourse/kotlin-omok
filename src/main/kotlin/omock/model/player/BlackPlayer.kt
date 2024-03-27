package omock.model.player

import omock.model.stone.Stone

data class BlackPlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player()
