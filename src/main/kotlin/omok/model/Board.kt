package omok.model

class Board {
    private val stonesBundle: MutableList<Stones> = mutableListOf()

    fun addStones(stones: Stones) {
        this.stonesBundle.add(stones)
    }

    fun checkDuplicate(stone: Stone) {
        require(
            stonesBundle.none { stones ->
                stones.stones.any { it.point == stone.point }
            },
        ) { "중복된 위치입니다." }
    }

    fun getAllStones(): List<Stone> {
        return stonesBundle.flatMap { it.stones }
    }

    companion object {
        private const val BOARD_SIZE = 15

        fun getSize(): Int = BOARD_SIZE
    }
}
