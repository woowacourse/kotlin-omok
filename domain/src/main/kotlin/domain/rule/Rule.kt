package domain.rule

import domain.*
import domain.rule.data.Direction
import domain.rule.data.Inclination
import domain.stone.*

interface Rule {

    val errorMessage: String

    fun checkRule(stones: Stones, justPlacedStone: Stone): Boolean
    private fun Pair<Int, Int>.isInRange(): Boolean {
        val (x, y) = this
        return x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE
    }

    private fun Pair<Int, Int>.isPlacedOnBlank(stones: Stones): Boolean {
        val (x, y) = this
        return Pair(x, y) !in stones.stones.map { Pair(it.point.x, it.point.y) }
    }

    private fun Pair<Int, Int>.isInSameColorStones(
        justPlacedStone: Stone,
        stones: Stones
    ): Boolean {
        val (x, y) = this
        return when (justPlacedStone) {
            is BlackStone -> Pair(x, y) in stones.blackStones.map { Pair(it.point.x, it.point.y) }
            else -> Pair(x, y) in stones.whiteStones.map { Pair(it.point.x, it.point.y) }
        }
    }

    private fun Pair<Int, Int>.isInOtherColorStones(
        justPlacedStone: Stone,
        stones: Stones
    ): Boolean {
        val (x, y) = this
        return when (justPlacedStone) {
            is BlackStone -> Pair(x, y) in stones.whiteStones.map { Pair(it.point.x, it.point.y) }
            else -> Pair(x, y) in stones.blackStones.map { Pair(it.point.x, it.point.y) }
        }
    }

    private fun createStone(stone: Stone, point: Point): Stone {
        return when (stone) {
            is BlackStone -> BlackStone(point)
            else -> WhiteStone(point)
        }
    }


    fun calculateContinuousStonesCountWithInclination(
        //최근_놓인_돌에서_다음_기울기로_연속되는_돌_개수
        stones: Stones,
        justPlacedStone: Stone,
        inclination: Inclination,
    ): Int {
        val (leftX, leftY) = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[0],
        )
        val (rightX, rightY) = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[1],
        )
        return Integer.max(
            kotlin.math.abs(rightX - leftX),
            kotlin.math.abs(rightY - leftY)
        ) - 1
    }


    private fun firstBlankWithThisDirection(
        //다음 방향으로 흑돌을 타고 갔을 때 최초의 빈칸
        stones: Stones,
        justPlacedStone: Stone,
        direction: Direction,
    ): Pair<Int, Int> {
        var nextX = justPlacedStone.point.x
        var nextY = justPlacedStone.point.y
        while (Pair(nextX, nextY).isInRange() && Pair(nextX, nextY).isInSameColorStones(
                justPlacedStone, stones
            ) && !Pair(nextX, nextY).isInOtherColorStones(
                justPlacedStone, stones
            )
        ) {
            nextX += direction.dx
            nextY += direction.dy
        }
        return Pair(nextX, nextY)
    }


    fun is5WhenPutStoneWithDirection(
        //다음_방향의_빈_칸에_뒀을때_5인가
        stones: Stones,
        justPlacedStone: Stone,
        direction: Direction,
    ): Boolean {
        val (x, y) = firstBlankWithThisDirection(stones, justPlacedStone, direction)
        if (Pair(x, y).isInRange() && Pair(x, y).isPlacedOnBlank(stones)) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return calculateContinuousStonesCountWithInclination(
                stones.addStone(createStone(justPlacedStone, Point(x, y))),
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
        val (x, y) =
            firstBlankWithThisDirection(
                stones,
                justPlacedStone,
                direction
            ) //다음 방향으로 흑돌을 타고 갔을 때 최초의 빈칸
        if (Pair(x, y).isInRange()) {
            val inclination = Inclination.values().first { it.directions.contains(direction) }
            return isOpen4WithThisInclination(  // 다음 기울기로 열린4인지 판단
                stones.addStone(createStone(justPlacedStone, Point(x, y))),
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
        val (leftX, leftY) = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[0],
        )
        val (rightX, rightY) = firstBlankWithThisDirection(
            stones,
            justPlacedStone,
            inclination.directions[1],
        )
        if (!Pair(leftX, leftY).isInRange() || !Pair(rightX, rightY).isInRange()) return false
        if (Pair(leftX, leftY).isPlacedOnBlank(stones) && Pair(rightX, rightY).isPlacedOnBlank(
                stones
            )
        ) {
            if (kotlin.math.abs(rightX - leftX) == 5 || kotlin.math.abs(rightY - leftY) == 5) return true
        }
        return false
    }
}