package omok.model

class Board() {
    val stones = Stones()

    fun add(stone: Stone) {
        require(stones.stones.all { it.point != stone.point }) { "중복입니다" }
        stones.add(stone)
    }

    fun lastStone(): Stone? {
        return stones.lastStone()
    }

    companion object {
        private const val BOARD_SIZE = 15

        fun getSize(): Int = BOARD_SIZE
    }
}
