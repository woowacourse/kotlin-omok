package domain

class BlackPlayer(override val color: Color = Color.BLACK) : Player() {
    override fun validateOmokRule(stones: Stones, stone: Stone): Boolean {
        return !RenjuRule.isThreeToThree(stones, stone) &&
            !RenjuRule.isFourToFour(stones, stone) &&
            RenjuRule.findScore(stones, stone) < LARGE_PLACE
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
