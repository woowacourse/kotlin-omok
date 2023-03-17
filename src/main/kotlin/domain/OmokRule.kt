package domain

interface OmokRule {
    val stones: Stones
    fun isThreeToThree(stone: Stone): Boolean
    fun isFourToFour(stone: Stone): Boolean
    fun findScore(stone: Stone): Int
}
