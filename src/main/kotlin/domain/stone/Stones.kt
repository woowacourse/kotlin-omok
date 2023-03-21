package domain.stone

class Stones(val stones: Set<Stone>) {
    val blackStones: Set<Stone>
        get() = stones.filterIsInstance<BlackStone>().toSet()
    val whiteStones: Set<Stone>
        get() = stones.filterIsInstance<WhiteStone>().toSet()

    fun addStone(stone: Stone): Stones = Stones(this.stones + stone)
}