package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

abstract class Finish : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState = throw IllegalStateException()

    override fun nextTurn(): PlayerState = throw IllegalStateException()
}
