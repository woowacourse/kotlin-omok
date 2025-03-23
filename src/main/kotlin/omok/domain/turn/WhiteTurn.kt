package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.Stone
import omok.domain.StoneState

class WhiteTurn(override val beforeTurn: StoneState) : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn {
        val stone = Stone(position, StoneState.WHITE)
        board.putStone(stone)
        if (board.checkOmok(position)) return Finished(StoneState.WHITE)
        return BlackTurn(StoneState.WHITE)
    }
}
