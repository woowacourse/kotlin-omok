package domain

class Players(private val value: List<Player>) {

    fun repeatTurn(stones: Stones, coordinateReader: CoordinateReader) {
        var index: Int = 0
        while (true) {
            val stone = value[index].place(stones, coordinateReader)
            if (stones.isWin(stone)) break
            index = turnPlayer(index)
        }
    }

    private fun turnPlayer(index: Int): Int = if (index == value.size - 1) 0 else index + 1
}
