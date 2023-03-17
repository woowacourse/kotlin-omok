package domain

interface OmokRule {

    fun checkForbidden(myboard: OmokBoard, stone: Stone): Boolean
}
