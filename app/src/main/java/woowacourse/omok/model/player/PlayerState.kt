package woowacourse.omok.model.player

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.model.stone.StoneState

interface PlayerState {
    val omokGame: OmokGame

    fun stoneState(): StoneState

    fun state(position: Position): PlayerState
}
