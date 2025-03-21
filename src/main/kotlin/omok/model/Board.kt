package omok.model

class Board {
    val stones: MutableSet<Stone2> = mutableSetOf()
    var lastStone: Stone2? = null

    fun add(newStone: Stone2) {
        require(
            !stones.map { stone -> stone.position }
                .contains(newStone.position),
        ) { ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED }
        stones.add(newStone)
        lastStone = newStone
    }

    companion object {
        private const val ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
    }
}
