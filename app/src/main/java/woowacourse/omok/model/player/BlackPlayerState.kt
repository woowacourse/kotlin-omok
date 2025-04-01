package woowacourse.omok.model.player

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.model.omokGame.TurnResult
import woowacourse.omok.model.stone.StoneState

class BlackPlayerState(
    override val omokGame: OmokGame,
) : PlayerState {
    private val stoneState: StoneState = StoneState.BLACK

    override fun stoneState(): StoneState = stoneState

    override fun state(position: Position): PlayerState {
        omokGame.placeStone(position, stoneState)
        val result = omokGame.blackResult(position, stoneState)
        return when (result) {
            TurnResult.CONTINUE -> WhitePlayerState(omokGame)
            TurnResult.DRAW -> Finish(omokGame, StoneState.NONE)
            else -> Finish(omokGame, stoneState)
        }
    }
}
