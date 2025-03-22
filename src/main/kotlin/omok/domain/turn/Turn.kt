package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.StoneState

interface Turn {
    val beforeTurn: StoneState?

    fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn
}
