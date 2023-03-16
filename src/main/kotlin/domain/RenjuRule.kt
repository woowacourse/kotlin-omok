package domain

class RenjuRule(val stones: List<Stone>) {
    fun isThreeToThree(stone: Stone): Boolean {
        return Stones.directions.sumOf { direction ->
            checkOpenFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= 2
    }

    fun isFourToFour(stone: Stone): Boolean {
        return Stones.directions.sumOf { direction ->
            checkFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= 2
    }

    private fun checkOpenFourForLine(start: Point, direction: Point): Int {
        return (0..3).count {
            isOpenFour(start + (direction * it), direction)
        }
    }

    private fun checkFourForLine(start: Point, direction: Point): Int {
        return (0..4).count {
            isFour(start + (direction * it), direction)
        }
    }

    private fun isOpenFour(start: Point, direction: Point): Boolean {
        if (!validateCheckBlock(start, direction)) return false

        val isOpened =
            stones.none { it.coordinate.point == start } && stones.none { it.coordinate.point == (start + (direction * 5)) }
        val isFour = (1..4).sumOf {
            getBlackStoneCount(start + (direction * it))
        } == 2
        return isOpened && isFour
    }

    private fun isFour(start: Point, direction: Point): Boolean {
        if (!validateCheckBlock(start, direction)) return false

        return (0..4).sumOf {
            getBlackStoneCount(start + (direction * it))
        } >= 3
    }

    private fun validateCheckBlock(start: Point, direction: Point): Boolean {
        repeat(5) {
            val next = (direction * it)
            if (Coordinate.from(start.x + next.x, start.y + next.y) == null) return false
        }
        return true
    }

    private fun getBlackStoneCount(start: Point): Int {
        val stone = stones.find { it.coordinate.point == start }
        if (stone == null || stone.color == Color.WHITE) return 0
        else if (stone.color == Color.BLACK) return 1
        return 0
    }
}
