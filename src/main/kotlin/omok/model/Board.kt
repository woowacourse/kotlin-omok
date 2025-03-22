package omok.model

import rule.wrapper.point.Point

class Board {
    val stones: MutableSet<Stone2> = mutableSetOf()
    var lastStone: Stone2? = null

    fun add(newStone: Stone2) {
        require(!stones.map { stone -> stone.position }.contains(newStone.position)) { ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED }

        checkViolation(newStone)

        stones.add(newStone)
        lastStone = newStone
    }

    private fun checkViolation(newStone: Stone2) {
        val blackPoints: List<Point> =
            stones.filter {
                    stone ->
                stone.color == Color.BLACK
            }.map { stone -> Point(stone.position.x, stone.position.y) }
        val whitePoints: List<Point> =
            stones.filter {
                    stone ->
                stone.color == Color.WHITE
            }.map { stone -> Point(stone.position.x, stone.position.y) }
    }

    companion object {
        private const val ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
    }
}
