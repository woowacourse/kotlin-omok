package domain

class RenjuRuleAdapter(stones : Stones) {
    private val renjuRule = RenjuRule(stones)
    fun isThreeToThree(stone: Stone): Boolean {
        return renjuRule.isThreeToThree(stone.coordinate.point)
    }

    fun isFourToFour(stone: Stone): Boolean {
        return renjuRule.isFourToFour(stone.coordinate.point)
    }

    fun findScore(stone: Stone): Int {
        return renjuRule.findScore(stone.coordinate,stone.color)
    }
}
