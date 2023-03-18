package domain

interface OmokRule {

    fun isMovable(myboard: OmokBoard, stone: Stone): Boolean
}
