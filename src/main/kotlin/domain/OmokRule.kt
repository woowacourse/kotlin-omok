package domain

import domain.board.OmokBoard

interface OmokRule {

    fun isMovable(myboard: OmokBoard, stone: Stone): Boolean
}
