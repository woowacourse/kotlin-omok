package omok.model

class BlackStonePlayer() {
    private val stones: Stones = Stones()

    fun getStones(): List<Stone> = stones.stones

    fun add(stone: Stone) {
        stones.add(stone)
    }

    fun lastStone(): Stone = stones.lastStone()
}
