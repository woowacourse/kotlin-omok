package omok.model

class Board {
    val stones: MutableSet<Stone2> = mutableSetOf()

    fun add(newStone: Stone2) {
        require(!stones.map { stone -> stone.position }.contains(newStone.position)) { ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED }
        stones.add(newStone)
    }

    fun filterStones(color: Color): List<Stone2> = stones.filter { stone -> stone.color == color }

    companion object {
        private const val ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
    }
}
