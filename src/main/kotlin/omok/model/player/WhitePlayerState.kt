package omok.model.player

import omok.model.board.Position
import omok.model.omokGame.GameResult
import omok.model.omokGame.OmokGame
import omok.model.stone.StoneState

class WhitePlayerState(
    override val omokGame: OmokGame,
) : PlayerState {
    private val stoneState: StoneState = StoneState.WHITE

    override fun state(position: Position): PlayerState {
        omokGame.placeStone(position, stoneState)
        val result = omokGame.getResult(position, stoneState)
        return when (result) {
            GameResult.CONTINUE -> BlackPlayerState(omokGame)
            GameResult.DRAW -> Finish(omokGame, StoneState.NONE)
            else -> Finish(omokGame, stoneState)
        }
    }
}
