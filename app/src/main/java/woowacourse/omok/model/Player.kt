package woowacourse.omok.model

class Player(val color: Color) {
    var isWin: Boolean = false
        private set

    fun getStone(coordinate: Coordinate): Stone {
        return Stone(color, coordinate)
    }

    fun win() {
        isWin = true
    }

    fun resetWinState() {
        isWin = false
    }
}
