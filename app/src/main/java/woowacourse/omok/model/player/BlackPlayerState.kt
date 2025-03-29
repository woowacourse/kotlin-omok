package omok.model.player

import omok.model.board.Position
import omok.model.omokGame.OmokGame
import omok.model.omokGame.TurnResult
import omok.model.stone.StoneState

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
