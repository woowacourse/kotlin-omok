package omok.domain

class Board {
    val grid: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE, {StoneType.EMPTY})}

    fun put(stone: Stone) {
        val row = stone.row
        val column = stone.column
        validateStoneRange(stone)
        if (grid[row][column] != StoneType.EMPTY) {
            throw IllegalArgumentException(ERROR_STONE_ALREADY_PLACED)
        }
        grid[row][column] = stone.color
    }

    private fun validateStoneRange(stone: Stone) {
        if (stone.row !in 0 until BOARD_SIZE || stone.column !in 0 until BOARD_SIZE) {
            throw IllegalArgumentException(ERROR_INVALID_PLACED.format(BOARD_SIZE))
        }
    }

    companion object {
        private const val BOARD_SIZE = 15
        private const val ERROR_STONE_ALREADY_PLACED = "이미 돌이 놓여진 위치입니다. 다시 입력해주세요."
        private const val ERROR_INVALID_PLACED = "유효하지 않은 돌의 위치입니다. 오목판은 0 이상 %d 미만이어야 합니다."
    }
}