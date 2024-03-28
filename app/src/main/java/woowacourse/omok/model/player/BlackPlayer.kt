package woowacourse.omok.model.player

import woowacourse.omok.model.stone.Stone

data class BlackPlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player()
