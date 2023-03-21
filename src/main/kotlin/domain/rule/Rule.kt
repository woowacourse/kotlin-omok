package domain.rule

import domain.BlackStone
import domain.Point
import domain.Stone
import domain.StoneFactory
import domain.Stones
import domain.X_MAX_RANGE
import domain.X_MIN_RANGE
import domain.Y_MAX_RANGE
import domain.Y_MIN_RANGE
import domain.rule.data.Direction
import domain.rule.data.Inclination

interface Rule {

    val errorMessage: String
    fun checkRule(stones: Stones, justPlacedStone: Stone): Boolean
    private fun Point.isInRange(): Boolean =
        x in X_MIN_RANGE..X_MAX_RANGE && y in Y_MIN_RANGE..Y_MAX_RANGE

    private fun Point.isPlacedOnBlank(stones: Stones): Boolean = this !in stones.stones.map { it.point }
    private fun Stone.isInSameColorStones(stones: Stones): Boolean {
        if (this is BlackStone) return this in stones.blackStones
        return this in stones.whiteStones
    }

    private fun Stone.isInOtherColorStones(stones: Stones): Boolean {
        if (this is BlackStone) return this in stones.whiteStones
        return this in stones.blackStones
    }


    fun calculateContinuousStonesCountWithInclination(
        //최근_놓인_돌에서_다음_기울기로_연속되는_돌_개수
        stones: Stones,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Int {
        val leftPoint = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[0],
        )
        val rightPoint = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[1],
        )
        return Integer.max(kotlin.math.abs(rightPoint.x - leftPoint.x), kotlin.math.abs(rightPoint.y - leftPoint.y)) - 1
    }


    private fun firstBlankWithThisDirection(
        //다음 방향으로 흑돌을 타고 갔을 때 최초의 빈칸
        stones: Stones,
        justPlacedStone: Stone,
        direction: Direction,
    ): Point {
        var nextStone: Stone = justPlacedStone
        var nextPoint = justPlacedStone.point
        while (nextPoint.isInRange() && nextStone.isInSameColorStones(stones) && !nextStone.isInOtherColorStones(stones)
        ) {
            nextPoint = nextPoint.addX(direction.dx)
            nextPoint = nextPoint.addY(direction.dy)
            nextStone = StoneFactory.createSameColorStone(justPlacedStone, nextPoint)
        }
        return nextPoint
    }


    fun is5WhenPutStoneWithDirection(
        //다음_방향의_빈_칸에_뒀을때_5인가
        stones: Stones,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val nextPoint = firstBlankWithThisDirection(stones, justPlacedStone, direction)
        if (nextPoint.isInRange() && nextPoint.isPlacedOnBlank(stones)) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return calculateContinuousStonesCountWithInclination(
                stones.addStone(StoneFactory.createSameColorStone(justPlacedStone, nextPoint)),
                justPlacedStone,
                inclination
            ) == 5
        }
        return false
    }


    fun isOpen4WhenPutStoneWithThisDirection(
        //다음 방향의 빈 칸에 흑돌을 뒀을때 열린4인가
        stones: Stones,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val nextPoint =
            firstBlankWithThisDirection(
                stones,
                justPlacedStone,
                direction
            ) //다음 방향으로 흑돌을 타고 갔을 때 최초의 빈칸
        if (nextPoint.isInRange()) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return isOpen4WithThisInclination(  // 다음 기울기로 열린4인지 판단
                stones.addStone(StoneFactory.createSameColorStone(justPlacedStone, nextPoint)),
                justPlacedStone,
                inclination
            )
        }
        return false
    }


    fun isOpen4WithThisInclination(
        //다음 기울기로 열린4인가
        stones: Stones,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Boolean {
        val leftPoint: Point = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[0],
        )
        val rightPoint = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[1],
        )
        if (!leftPoint.isInRange() || !rightPoint.isInRange()) return false
        if (leftPoint.isPlacedOnBlank(stones) && rightPoint.isPlacedOnBlank(stones)) {
            if (kotlin.math.abs(rightPoint.x - leftPoint.x) == 5 || kotlin.math.abs(rightPoint.y - leftPoint.y) == 5) return true
        }
        return false
    }
}