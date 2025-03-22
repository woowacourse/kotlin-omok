package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.StoneState

class WhiteTurn : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn {
        val stone = StoneState.WHITE
        board.putStone(position, stone)
        if (board.checkOmok(position)) return Finished()
        return BlackTurn()
    }
}
