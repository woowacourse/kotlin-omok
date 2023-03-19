package domain.rule

import domain.Point
import domain.Stone
import domain.XCoordinate
import domain.YCoordinate
import domain.rule.data.Direction
import domain.rule.data.Inclination

abstract class BlackRule {
    abstract fun checkRule(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean
    private fun Point.isInRange(): Boolean =
        x in XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE && y in YCoordinate.Y_MIN_RANGE..YCoordinate.Y_MAX_RANGE

    private fun Stone.isPlacedOnBlank(stones: Set<Stone>): Boolean = this !in stones


    protected fun calculateContinuousBlackStonesCountFromRecentStoneWithInclination(
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
        return Integer.max(kotlin.math.abs(rightPoint.x - leftPoint.x), kotlin.math.abs(rightPoint.y - leftPoint.y)) - 1
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


    protected fun is5WhenPutStoneWithDirection(
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


    protected fun isOpen4WhenPutBlackStoneWithThisDirection(
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


    protected fun isOpen4WithThisInclination(
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
}