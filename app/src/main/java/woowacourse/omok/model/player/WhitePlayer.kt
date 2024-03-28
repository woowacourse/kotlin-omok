package omock.model.player

import omock.model.stone.Stone

data class WhitePlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player()
