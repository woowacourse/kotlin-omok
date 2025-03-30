package woowacourse.omok.model.player

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.model.stone.StoneState

class Finish(
    override val omokGame: OmokGame,
    private val stoneState: StoneState,
) : PlayerState {
    override fun stoneState(): StoneState = stoneState

    override fun state(position: Position): PlayerState = throw IllegalStateException("이 플레이어의 게임은 끝이 났습니다.")

    fun winner(): StoneState = stoneState
}
