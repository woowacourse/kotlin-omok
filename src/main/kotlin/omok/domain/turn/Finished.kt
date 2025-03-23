package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.StoneState

class Finished(override val beforeTurn: StoneState) : Turn {
    override fun putStone(
        position: Position,
        board: OmokBoard,
    ): PutStoneResult {
        throw IllegalStateException()
    }
}
