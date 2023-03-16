package domain

object Rule {

    fun canPut(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean =
        !(
            isBlack33(blackStones, whiteStones, nextStone) || isBlack44(
                blackStones,
                whiteStones,
                nextStone,
            ) || isBlackLongMok(
                blackStones,
                nextStone,
            )
            )

    private fun isBlack33(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextBlackStones = blackStones + nextStone
        var count33 = 0
        if (isHorizontalOpen4(nextBlackStones, whiteStones, nextStone)) count33++
        if (isVerticalOpen4(nextBlackStones, whiteStones, nextStone)) count33++
        if (isUpperRightOpen4(nextBlackStones, whiteStones, nextStone)) count33++
        if (isUpperLeftOpen4(nextBlackStones, whiteStones, nextStone)) count33++
        return count33 >= 2
    }

    // 가로 판단
    private fun isHorizontalOpen4(blackStones: Set<Stone>, whiteStones: Set<Stone>, justPlacedStone: Stone): Boolean {
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.LEFT)) return true
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.RIGHT)) return true
        return false
    }

    private fun isVerticalOpen4(blackStones: Set<Stone>, whiteStones: Set<Stone>, justPlacedStone: Stone): Boolean {
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.UP)) return true
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.DOWN)) return true
        return false
    }

    private fun isUpperRightOpen4(blackStones: Set<Stone>, whiteStones: Set<Stone>, justPlacedStone: Stone): Boolean {
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.UP_RIGHT)) return true
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.DOWN_LEFT)) return true
        return false
    }

    private fun isUpperLeftOpen4(blackStones: Set<Stone>, whiteStones: Set<Stone>, justPlacedStone: Stone): Boolean {
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.UP_LEFT)) return true
        if (다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones, whiteStones, justPlacedStone, Direction.DOWN_RIGHT)) return true
        return false
    }

    private fun 다음_방향의_빈_칸에_뒀을때_열린4인가(blackStones: Set<Stone>, whiteStones: Set<Stone>, justPlacedStone: Stone, direction: Direction): Boolean {
        var (nextX, nextY) = justPlacedStone.x to justPlacedStone.y
        while ((nextX to nextY).isInRange() && Stone(nextX, nextY) !in whiteStones && Stone(nextX, nextY) in blackStones) {
            nextX += direction.dx
            nextY += direction.dy
        }
        if ((nextX to nextY).isInRange()) {
            return when (direction) {
                Direction.LEFT, Direction.RIGHT -> 다음_기울기로_열린4인가(blackStones + Stone(nextX, nextY), whiteStones, justPlacedStone, Inclination.HORIZONTAL)
                Direction.UP, Direction.DOWN -> 다음_기울기로_열린4인가(blackStones + Stone(nextX, nextY), whiteStones, justPlacedStone, Inclination.VERTICAL)
                Direction.UP_LEFT, Direction.DOWN_RIGHT -> 다음_기울기로_열린4인가(blackStones + Stone(nextX, nextY), whiteStones, justPlacedStone, Inclination.UPPER_LEFT_DIAGONAL)
                Direction.UP_RIGHT, Direction.DOWN_LEFT -> 다음_기울기로_열린4인가(blackStones + Stone(nextX, nextY), whiteStones, justPlacedStone, Inclination.UPPER_RIGHT_DIAGONAL)
            }
        }
        return false
    }

    private fun 다음_기울기로_열린4인가(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Boolean {
        var (leftX, leftY) = justPlacedStone.x to justPlacedStone.y
        while ((leftX to leftY).isInRange() &&
            Stone(leftX, leftY) !in whiteStones &&
            Stone(leftX, leftY) in blackStones
        ) {
            leftX -= inclination.dx
            leftY -= inclination.dy
        }
        var (rightX, rightY) = justPlacedStone.x to justPlacedStone.y
        while ((rightX to rightY).isInRange() &&
            Stone(rightX, rightY) !in whiteStones &&
            Stone(rightX, rightY) in blackStones
        ) {
            rightX += inclination.dx
            rightY += inclination.dy
        }
        if (!(leftX to leftY).isInRange() || !(rightX to rightY).isInRange()) return false
        val leftStone = Stone(leftX, leftY)
        val rightStone = Stone(rightX, rightY)
        if (leftStone.isPlacedOnBlank(blackStones + whiteStones) && rightStone.isPlacedOnBlank(blackStones + whiteStones)) {
            if (rightX - leftX == 5 || rightY - leftY == 5) return true
        }
        return false
    }

    private fun isBlack44(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        return false
    }

    private fun isBlackLongMok(stones: Set<Stone>, nextStone: Stone): Boolean {
        return false
    }

    private fun Pair<Char, Int>.isInRange(): Boolean =
        first in XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE && second in YCoordinate.Y_MIN_RANGE..YCoordinate.Y_MAX_RANGE

    private fun Stone.isPlacedOnBlank(stones: Set<Stone>): Boolean = this !in stones
}

private enum class Direction(val dx: Int, val dy: Int) {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1),
}

private enum class Inclination(val dx: Int, val dy: Int) {
    HORIZONTAL(1, 0), VERTICAL(0, 1), UPPER_RIGHT_DIAGONAL(1, 1), UPPER_LEFT_DIAGONAL(-1, 1)
}
