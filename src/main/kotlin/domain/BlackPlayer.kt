package domain

class BlackPlayer(override val color: Color = Color.BLACK) : Player() {
    override fun validateRenju(stones: Stones, stone: Stone): Boolean {
        return !stones.threeToThree(stone)
    }
}
