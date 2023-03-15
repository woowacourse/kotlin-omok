package model.domain

object OmokRule {
    private const val START = 0
    private const val END = 14
    private const val NOTHING = 0
    private const val OMOK = 5
    private val COORDINATION_SYSTEM_RANGE = START..END
    fun isOmok(board: Board, location: Location, stone: Stone): Boolean {
        val row = location.coordinationX.value
        val col = location.coordinationY.value
        val directions: List<Pair<Int, Int>> = listOf(
            Pair(-1, 1),
            Pair(0, 1),
            Pair(1, 1),
            Pair(1, 0),
        )

        for ((dx, dy) in directions) {
            var count = NOTHING
            var (x, y) = Pair(row, col)

            x -= dx * 4
            y -= dy * 4

            for (cell in 0 until 9) {
                if ((x in COORDINATION_SYSTEM_RANGE) and (y in COORDINATION_SYSTEM_RANGE)) {
                    if (board.get(Location(Coordination.from(x)!!, Coordination.from(y)!!)) == stone) {
                        count++
                        if (count >= OMOK) return true
                    } else {
                        count = NOTHING
                    }

                    x += dx
                    y += dy
                }
            }
        }

        return false
    }
}
