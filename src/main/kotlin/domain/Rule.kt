package domain

import java.lang.Integer.max

object Rule {

    fun checkPutBlackStone(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone) {
        require(!isBlack33(blackStones, whiteStones, nextStone)) { "흑돌은 33이면 안됩니다." }
        require(!isBlack44(blackStones, whiteStones, nextStone)) { "흑돌은 44면 안됩니다." }
        require(!isBlackLongMok(blackStones, nextStone)) { "흑돌은 장목이면 안됩니다." }
    }

    private fun isBlack33(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextBlackStones = blackStones + nextStone
        var count33 = 0
        Inclination.values().forEach { inclination ->
            if (inclination.directions.any {
                    다음_방향의_빈_칸에_뒀을때_열린4인가(
                        nextBlackStones,
                        whiteStones,
                        nextStone,
                        it,
                    )
                }
            ) {
                count33++
            }
        }
        return count33 >= 2
    }

    private fun 다음_방향의_빈_칸에_뒀을때_열린4인가(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val (nextX, nextY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(blackStones, justPlacedStone, direction)
        if ((nextX to nextY).isInRange()) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return 다음_기울기로_열린4인가(blackStones + Stone(nextX, nextY), whiteStones, justPlacedStone, inclination)
        }
        return false
    }

    private fun 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
        blackStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Pair<Char, Int> {
        var (nextX, nextY) = justPlacedStone.x to justPlacedStone.y
        while ((nextX to nextY).isInRange() && Stone(nextX, nextY) in blackStones) {
            nextX += direction.dx
            nextY += direction.dy
        }
        return nextX to nextY
    }

    private fun 다음_기울기로_열린4인가(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Boolean {
        val (leftX, leftY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val (rightX, rightY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        if (!(leftX to leftY).isInRange() || !(rightX to rightY).isInRange()) return false
        val leftStone = Stone(leftX, leftY)
        val rightStone = Stone(rightX, rightY)
        if (leftStone.isPlacedOnBlank(blackStones + whiteStones) && rightStone.isPlacedOnBlank(blackStones + whiteStones)) {
            if (kotlin.math.abs(rightX - leftX) == 5 || kotlin.math.abs(rightY - leftY) == 5) return true
        }
        return false
    }

    private fun isBlack44(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextBlackStones = blackStones + nextStone
        var count44 = 0
        Inclination.values().forEach {
            if (다음_기울기로_5가_될_수_있는_4인가(nextBlackStones, whiteStones, nextStone, it)) {
                count44++
            } else {
                it.directions.forEach { direction ->
                    if (다음_방향의_빈_칸에_뒀을때_5인가(nextBlackStones, whiteStones, nextStone, direction)) count44++
                }
            }
        }
        return count44 >= 2
    }

    private fun 다음_기울기로_5가_될_수_있는_4인가(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Boolean {
        val (leftX, leftY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val (rightX, rightY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        if (((leftX to leftY).isInRange() && Stone(leftX, leftY).isPlacedOnBlank(blackStones + whiteStones)) ||
            ((rightX to rightY).isInRange() && Stone(rightX, rightY).isPlacedOnBlank(blackStones + whiteStones))
        ) {
            if (kotlin.math.abs(rightX - leftX) == 5 || kotlin.math.abs(rightY - leftY) == 5) return true
        }
        return false
    }

    private fun 다음_방향의_빈_칸에_뒀을때_5인가(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val (nextX, nextY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(blackStones, justPlacedStone, direction)
        if ((nextX to nextY).isInRange() && Stone(nextX, nextY).isPlacedOnBlank(blackStones + whiteStones)) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return 최근_놓인_돌에서_다음_기울기로_연속되는_흑돌_개수(blackStones + Stone(nextX, nextY), justPlacedStone, inclination) == 5
        }
        return false
    }

    private fun 최근_놓인_돌에서_다음_기울기로_연속되는_흑돌_개수(
        blackStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Int {
        val (leftX, leftY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val (rightX, rightY) = 다음_방향으로_흑돌을_타고_갔을_때_다음_빈칸(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        return max(kotlin.math.abs(rightX - leftX), kotlin.math.abs(rightY - leftY)) - 1
    }

    private fun isBlackLongMok(blackStones: Set<Stone>, nextStone: Stone): Boolean =
        Inclination.values().any { 최근_놓인_돌에서_다음_기울기로_연속되는_흑돌_개수(blackStones + nextStone, nextStone, it) > 5 }

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

private enum class Inclination(val dx: Int, val dy: Int, val directions: List<Direction>) {
    HORIZONTAL(1, 0, listOf(Direction.LEFT, Direction.RIGHT)),
    VERTICAL(0, 1, listOf(Direction.UP, Direction.DOWN)),
    UPPER_RIGHT_DIAGONAL(1, 1, listOf(Direction.UP_RIGHT, Direction.DOWN_LEFT)),
    UPPER_LEFT_DIAGONAL(-1, 1, listOf(Direction.UP_LEFT, Direction.DOWN_RIGHT)),
}
