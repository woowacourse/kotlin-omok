package domain

class RenjuRule(val stones: List<Stone>) {

    fun threeToThree(stone: Stone): Boolean {
        return Stones.directions.count { direction ->
            checkOpenFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= 2
    }

    private fun checkOpenFourForLine(start: Point, direction: Point): Boolean {
        for (i in 0..3) {
            if (isOpenFour(start + (direction * i), direction)) return true
        }
        return false
    }

    private fun isOpenFour(start: Point, direction: Point): Boolean {
        repeat(5) {
            val next = (direction * it)
            if (Coordinate.from(start.x + next.x, start.y + next.y) == null) return false
        }

        val isOpened =
            stones.none { it.coordinate.point == start } && stones.none { it.coordinate.point == (start + (direction * 5)) }
        var blackStoneCount = 0
        for (i in 1..4) {
            val stone = stones.find {
                it.coordinate.point == start + (direction * i)
            }
            if (stone == null) continue
            else if (stone.color == Color.BLACK) blackStoneCount++
            else if (stone.color == Color.WHITE) return false
        }
        val isFour = blackStoneCount == 2
        return isOpened && isFour
    }

    fun fourToFour(stone: Stone): Boolean {
        return Stones.directions.sumOf { direction ->
            checkFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= 2
    }

    private fun checkFourForLine(start: Point, direction: Point): Int {
        return (0..4).sumOf {
            (if (isFour(start + (direction * it), direction) >= 3) 1 else 0) as Int
        }
    }

    private fun isFour(start: Point, direction: Point): Int {
        repeat(5) {
            val next = (direction * it)
            if (Coordinate.from(start.x + next.x, start.y + next.y) == null) return 0
        }

        var blackStoneCount = 0
        for (i in 0..4) {
            val stone = stones.find {
                it.coordinate.point == start + (direction * i)
            }
            if (stone == null) continue
            else if (stone.color == Color.BLACK) blackStoneCount++
            else if (stone.color == Color.WHITE) return 0
        }
        return blackStoneCount
    }
}
