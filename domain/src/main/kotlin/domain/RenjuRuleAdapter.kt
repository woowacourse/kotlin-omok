package domain

class RenjuRuleAdapter(private val renjuRule: RenjuRuleInterface) : RenjuRuleInterface {
    override fun isThreeToThree(stone: Stone, stones: Stones): Boolean {
        return renjuRule.isThreeToThree(stone, stones)
    }

    override fun isFourToFour(stone: Stone, stones: Stones): Boolean {
        return renjuRule.isFourToFour(stone, stones)
    }

    override fun isOverFive(stoneScore: Int): Boolean {
        return renjuRule.isOverFive(stoneScore)
    }
}
