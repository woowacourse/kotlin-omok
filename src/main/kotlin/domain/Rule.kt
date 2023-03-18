package domain

import java.lang.Integer.max

object Rule {

    fun checkPutBlackStone(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone) {
        require(!isBlack33(blackStones, whiteStones, nextStone)) { "흑돌은 33이면 안됩니다." }
        require(!isBlack44(blackStones, whiteStones, nextStone)) { "흑돌은 44면 안됩니다." }
        require(!isBlackLongMok(blackStones, nextStone)) { "흑돌은 장목이면 안됩니다." }
    }

    private fun isBlack33(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        nextStone: Stone
    ): Boolean {    // 참고로, 열린 4가 만들어 질 수 있는 것을 3이라고 한다
        val nextBlackStones = blackStones + nextStone
        var count33 = 0
        Inclination.values().forEach { inclination ->
            if (inclination.directions.any {
                    isOpen4WhenPutBlackStoneWithThisDirection(
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

    private fun isOpen4WhenPutBlackStoneWithThisDirection(
        //다음 방향의 빈 칸에 흑돌을 뒀을때 열린4인가
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val nextPoint =
            firstBlackStonesBlankWithThisDirection(blackStones, justPlacedStone, direction) //다음 방향으로 흑돌을 타고 갔을 때 최초의 빈칸
        if (nextPoint.isInRange()) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return isOpen4WithThisInclination(  // 다음 기울기로 열린4인지 판단
                blackStones + Stone(nextPoint),
                whiteStones,
                justPlacedStone,
                inclination
            )
        }
        return false
    }

    private fun firstBlackStonesBlankWithThisDirection(
        //다음 방향으로 흑돌을 타고 갔을 때 최초의 빈칸
        blackStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Point {
        var nextPoint: Point = justPlacedStone.point
        while (nextPoint.isInRange() && Stone(nextPoint) in blackStones) {
            nextPoint = nextPoint.addX(direction.dx)
            nextPoint = nextPoint.addY(direction.dy)
        }
        return nextPoint
    }

    private fun isOpen4WithThisInclination(
        //다음 기울기로 열린4인가
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Boolean {
        val leftPoint: Point = firstBlackStonesBlankWithThisDirection(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val rightPoint = firstBlackStonesBlankWithThisDirection(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        if (!leftPoint.isInRange() || !rightPoint.isInRange()) return false
        val leftStone = Stone(leftPoint)
        val rightStone = Stone(rightPoint)
        if (leftStone.isPlacedOnBlank(blackStones + whiteStones) && rightStone.isPlacedOnBlank(blackStones + whiteStones)) {
            if (kotlin.math.abs(rightPoint.x - leftPoint.x) == 5 || kotlin.math.abs(rightPoint.y - leftPoint.y) == 5) return true
        }
        return false
    }

    private fun isBlack44(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextBlackStones = blackStones + nextStone
        var count44 = 0
        Inclination.values().forEach {
            if (is4CanBe5WithThisInclination(nextBlackStones, whiteStones, nextStone, it)) {
                count44++
            } else {
                it.directions.forEach { direction ->
                    if (is5WhenPutStoneWithDirection(nextBlackStones, whiteStones, nextStone, direction)) count44++
                }
            }
        }
        return count44 >= 2
    }

    private fun is4CanBe5WithThisInclination(
        //다음_기울기로_5가_될_수_있는_4인가
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Boolean {
        val leftPoint = firstBlackStonesBlankWithThisDirection(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val rightPoint = firstBlackStonesBlankWithThisDirection(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        if ((leftPoint.isInRange() && Stone(leftPoint).isPlacedOnBlank(blackStones + whiteStones)) ||
            (rightPoint.isInRange() && Stone(rightPoint).isPlacedOnBlank(blackStones + whiteStones))
        ) {
            if (kotlin.math.abs(rightPoint.x - leftPoint.x) == 5 || kotlin.math.abs(rightPoint.y - leftPoint.y) == 5) return true
        }
        return false
    }

    private fun is5WhenPutStoneWithDirection(
        //다음_방향의_빈_칸에_뒀을때_5인가
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val nextPoint = firstBlackStonesBlankWithThisDirection(blackStones, justPlacedStone, direction)
        if (nextPoint.isInRange() && Stone(nextPoint).isPlacedOnBlank(blackStones + whiteStones)) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return calculateContinuousBlackStonesCountFromRecentStoneWithInclination(
                blackStones + Stone(nextPoint),
                justPlacedStone,
                inclination
            ) == 5
        }
        return false
    }

    private fun calculateContinuousBlackStonesCountFromRecentStoneWithInclination(
        //최근_놓인_돌에서_다음_기울기로_연속되는_흑돌_개수
        blackStones: Set<Stone>,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Int {
        val leftPoint = firstBlackStonesBlankWithThisDirection(
            blackStones,
            justPlacedStone,
            inclination.directions[0],
        )
        val rightPoint = firstBlackStonesBlankWithThisDirection(
            blackStones,
            justPlacedStone,
            inclination.directions[1],
        )
        return max(kotlin.math.abs(rightPoint.x - leftPoint.x), kotlin.math.abs(rightPoint.y - leftPoint.y)) - 1
    }

    private fun isBlackLongMok(blackStones: Set<Stone>, nextStone: Stone): Boolean =
        Inclination.values().any {
            calculateContinuousBlackStonesCountFromRecentStoneWithInclination(
                blackStones + nextStone,
                nextStone,
                it
            ) > 5
        }

    private fun Point.isInRange(): Boolean =
        x in XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE && y in YCoordinate.Y_MIN_RANGE..YCoordinate.Y_MAX_RANGE

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

private enum class Inclination(val directions: List<Direction>) {
    HORIZONTAL(listOf(Direction.LEFT, Direction.RIGHT)),
    VERTICAL(listOf(Direction.UP, Direction.DOWN)),
    UPPER_RIGHT_DIAGONAL(listOf(Direction.UP_RIGHT, Direction.DOWN_LEFT)),
    UPPER_LEFT_DIAGONAL(listOf(Direction.UP_LEFT, Direction.DOWN_RIGHT)),
}
