package omok.model.player

import omok.model.OmokGame
import omok.model.board.Position
import omok.model.stone.StoneState

class Finish(
    override val omokGame: OmokGame,
    private val stoneState: StoneState,
) : PlayerState {
    override fun nextTurn(position: Position): PlayerState = throw IllegalStateException("이 플레이어의 게임은 끝이 났습니다.")

    fun winner(): StoneState = stoneState
}
