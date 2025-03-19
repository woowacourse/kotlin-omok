package omok.model

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.stone.StoneState

interface Rule {
    fun findOmok(
        position: Position,
        stone: StoneState,
        board: OmokBoard,
    ): Boolean
}
