package model.domain

object OmokRule {
    fun isOmok(board: Board, location: Location, stone: Stone): Boolean {
        val row = location.coordinationX.value
        val col = location.coordinationY.value

        val directions = listOf(
            Pair(-1, 1),
            Pair(0, 1),
            Pair(1, 1),
            Pair(1, 0),
        )

        return directions.any { (dx, dy) ->
            var count = 0
            var (x, y) = Pair(row, col)

            while (board.get(location) == stone) {
                count++
                x += dx
                y += dy
            }

            x = row - dx
            y = col - dy

            while (board.get(location) == stone) {
                count++
                x -= dx
                y -= dy
            }

            count >= 5
        }
    }
}
