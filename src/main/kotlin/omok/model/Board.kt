package omok.model

import RenjuRule

class Board(val stones: Stones, var width: Int = DEFAULT_BOARD_WIDTH, var height: Int = DEFAULT_BOARD_HEIGHT) {
    private val rule: Rule = RenjuRule(stones)

    fun putStone(stone: Stone): Boolean {
        if (canPutStone(stone)) {
            return stones.putStone(stone)
        }
        return false
    }

    private fun canPutStone(stone: Stone): Boolean {
        return validateStoneCoordinate(stone.coordinate) && !rule.checkInvalid(stone)
    }

    private fun validateStoneCoordinate(coordinate: Coordinate): Boolean {
        val x = coordinate.x.value
        val y = coordinate.y.value
        return (x in BOARD_START_INDEX..width && y in BOARD_START_INDEX..height)
    }

    companion object {
        const val BOARD_START_INDEX: Int = 1
        const val DEFAULT_BOARD_WIDTH: Int = 15
        const val DEFAULT_BOARD_HEIGHT: Int = 15
    }
}
