package domain

class BlackPlayer(override val color: Color = Color.BLACK) : Player() {
    override fun validateRenju(stones: Stones, stone: Stone): Boolean {
        return !stones.renjuRule.isThreeToThree(stone) && !stones.renjuRule.isFourToFour(stone) && stones.renjuRule.findScore(stone) < LARGE_PLACE
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
