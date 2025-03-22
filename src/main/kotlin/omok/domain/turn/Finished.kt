package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position

class Finished : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): Turn {
        throw IllegalStateException()
    }
}
