package omok.model

class OmokGame(val board: Board) {
    fun start(coordinate: () -> Coordinate, showBoard: (Board) -> Unit) {
        while (true) {
            turn(coordinate, showBoard)
        }
    }

    fun turn(coordinate: () -> Coordinate, showBoard: (Board) -> Unit) {
        board.addStone(board.getNextColor(), getValidCoordinate(coordinate))
        showBoard(board)
    }

    private fun getValidCoordinate(getCoordinate: () -> Coordinate): Coordinate {
        return getValidateValue(getCoordinate, board::canAdd)
    }

    private fun <T> getValidateValue(getValue: () -> (T), condition: (T) -> Boolean): T {
        while (true) {
            runCatching {
                val value = getValue()
                if (condition(value)) return value
            }.onFailure {
                println(it.message)
            }
        }
    }
}
