package omok.model.player

import omok.model.GameResult
import omok.model.OmokGame
import omok.model.board.Position
import omok.model.stone.StoneState

class WhitePlayerState(
    override val omokGame: OmokGame,
) : PlayerState {
    private val stoneState: StoneState = StoneState.WHITE

    override fun nextTurn(position: Position): PlayerState {
        omokGame.placeStone(position, stoneState)
        val result = omokGame.getResult(position, stoneState)
        return when (result) {
            GameResult.CONTINUE -> BlackPlayerState(omokGame)
            GameResult.DRAW -> Finish(omokGame, StoneState.NONE)
            else -> Finish(omokGame, stoneState)
        }
    }
}
