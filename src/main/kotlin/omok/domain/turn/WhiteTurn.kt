package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.StoneState

class WhiteTurn(override val beforeTurn: StoneState) : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn {
        val stone = StoneState.WHITE
        board.putStone(position, stone)
        if (board.checkOmok(position)) return Finished(stone)
        return BlackTurn(stone)
    }
}
