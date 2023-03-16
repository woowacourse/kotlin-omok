package domain

import domain.board.OmokBoard

interface OmokRule {

    fun checkForbidden(myboard: OmokBoard, stone: Stone): Boolean
}
