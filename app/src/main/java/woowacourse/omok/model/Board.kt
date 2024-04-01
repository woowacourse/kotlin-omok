package woowacourse.omok.model

class Board(
    val stones: Stones,
    private val rule: Rule,
    val width: Int = DEFAULT_BOARD_WIDTH,
    val height: Int = DEFAULT_BOARD_HEIGHT,
) {
    fun putStone(stone: Stone): StoneState {
        val state = determineStoneState(stone)
        return when (state) {
            is StoneState.SuccessfulPlaced -> {
                stones.putStone(stone)
                state
            }

            is StoneState.FailedPlaced -> state
        }
    }

    private fun determineStoneState(stone: Stone): StoneState {
        return when {
            !rule.checkPlaceable(stone) -> StoneState.FailedPlaced(ERROR_FORBIDDEN_MESSAGE)
            validateStoneCoordinate(stone.coordinate) -> StoneState.FailedPlaced(ERROR_OUT_OF_RANGE_BOARD_MESSAGE)
            validateOccupiedCoordinate(stone.coordinate) -> StoneState.FailedPlaced(ERROR_OCCUPIED_MESSAGE)
            else -> StoneState.SuccessfulPlaced
        }
    }

    private fun validateStoneCoordinate(coordinate: Coordinate): Boolean {
        val x = coordinate.x.value
        val y = coordinate.y.value
        return (x !in BOARD_START_INDEX..width && y !in BOARD_START_INDEX..height)
    }

    private fun validateOccupiedCoordinate(coordinate: Coordinate): Boolean {
        return stones.stones.any { it.coordinate == coordinate }
    }

    companion object {
        const val BOARD_START_INDEX: Int = 1
        const val DEFAULT_BOARD_WIDTH: Int = 15
        const val DEFAULT_BOARD_HEIGHT: Int = 15
        const val ERROR_FORBIDDEN_MESSAGE = "금수를 두었습니다."
        const val ERROR_OUT_OF_RANGE_BOARD_MESSAGE = "보드의 사이즈를 벗어났습니다."
        const val ERROR_OCCUPIED_MESSAGE = "이미 돌이 착수된 위치입니다."
    }
}
