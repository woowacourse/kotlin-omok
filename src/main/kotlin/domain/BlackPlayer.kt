package domain

class BlackPlayer(override val color: Color = Color.BLACK) : Player() {
    override fun validateRenju(stones: Stones, stone: Stone): Boolean {
        return !stones.renjuRule.threeToThree(stone) && !stones.renjuRule.fourToFour(stone) && stones.findScore(stone) < LARGE_PLACE
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
