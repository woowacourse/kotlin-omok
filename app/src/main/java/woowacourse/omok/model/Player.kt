package woowacourse.omok.model

class Player(val color: Color) {
    var isWin: Boolean = false
        private set

    fun getStone(
        row: Int,
        col: Int,
    ): Stone {
        val coordinate = Coordinate(Row(row), Column(col))
        return Stone(color, coordinate)
    }

    fun win() {
        isWin = true
    }

    fun resetWinState() {
        isWin = false
    }

    fun checkOmok(
        stones: Stones,
        stone: Stone,
    ) {
        isWin = stones.findOmok(stone)
    }
}
