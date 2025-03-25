package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.place.Place

interface OmokRule {
    fun isProtected(
        place: Place,
        board: OmokBoard,
    ): Boolean
}
