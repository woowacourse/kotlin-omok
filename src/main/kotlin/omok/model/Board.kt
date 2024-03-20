package omok.model

class Board() {
    var layout: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.EMPTY } }
    var lastPosition: Position? = null

    fun placeStone(
        position: Position,
        stoneType: StoneType,
    ) {
        if (layout[position.coordinate.x][position.coordinate.y] == StoneType.EMPTY) {
            layout[position.coordinate.x][position.coordinate.y] = stoneType
            lastPosition = position
        } else {
            throw IllegalArgumentException("이미 돌이 놓인 자리입니다.")
        }
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
