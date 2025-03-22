package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position

interface Turn {
    fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn
}
