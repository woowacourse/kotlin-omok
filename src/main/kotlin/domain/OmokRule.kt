package domain

interface OmokRule {

    fun isForbidden(myboard: OmokBoard, stone: Stone): Boolean
}
