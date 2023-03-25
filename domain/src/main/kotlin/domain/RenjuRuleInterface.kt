package domain

interface RenjuRuleInterface {
    fun isThreeToThree(stone: Stone, stones: Stones): Boolean
    fun isFourToFour(stone: Stone, stones: Stones): Boolean
    fun isOverFive(stoneScore : Int) : Boolean
}