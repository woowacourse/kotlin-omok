package woowacourse.omok.model.player

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.model.omokGame.TurnResult
import woowacourse.omok.model.stone.StoneState

class WhitePlayerState(
    override val omokGame: OmokGame,
) : PlayerState {
    private val stoneState: StoneState = StoneState.WHITE

    override fun stoneState(): StoneState = stoneState

    override fun state(position: Position): PlayerState {
        omokGame.placeStone(position, stoneState)
        val result = omokGame.whiteResult(position, stoneState)
        return when (result) {
            TurnResult.CONTINUE -> BlackPlayerState(omokGame)
            TurnResult.DRAW -> Finish(omokGame, StoneState.NONE)
            else -> Finish(omokGame, stoneState)
        }
    }
}
