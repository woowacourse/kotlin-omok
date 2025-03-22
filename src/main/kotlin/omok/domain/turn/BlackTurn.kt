package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.StoneState

class BlackTurn : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn {
        val stone = StoneState.BLACK
        if (board.canPlace(position)) return this
        board.putStone(position, stone)
        if (board.checkOmok(position)) return Finished()
        return WhiteTurn()
    }
}
