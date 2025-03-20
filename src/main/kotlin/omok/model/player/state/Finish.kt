package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

abstract class Finish : PlayerState {
    override fun putCount(): Int = throw IllegalStateException()

    override fun stoneState(): StoneState = throw IllegalStateException()

    override fun isPlaceTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState = throw IllegalStateException()

    abstract fun isWinner(): Finish

    override fun stop() = throw IllegalStateException()

    override fun nextTurn(): PlayerState = throw IllegalStateException()
}
