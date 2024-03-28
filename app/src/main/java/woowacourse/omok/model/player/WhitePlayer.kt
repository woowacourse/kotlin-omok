package woowacourse.omok.model.player

import woowacourse.omok.model.stone.Stone

data class WhitePlayer(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Player()
