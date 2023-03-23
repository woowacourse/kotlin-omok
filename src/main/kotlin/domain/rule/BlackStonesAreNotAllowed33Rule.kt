package domain.rule

import domain.*
import domain.state.BlackTurn
import domain.state.State

object BlackStonesAreNotAllowed33Rule : DetailRule {
    override fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean {
        if (state is BlackTurn) {
            return !isBlack33(state.blackStones, state.whiteStones, nextStone)
        }
        return true
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
        if (Point(nextX, nextY) in Board) {
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
        while (Point(nextX, nextY) in Board && Stone(nextX, nextY) in blackStones) {
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
        if (Point(leftX, leftY) !in Board || Point(rightX, rightY) !in Board) return false
        val leftStone = Stone(leftX, leftY)
        val rightStone = Stone(rightX, rightY)
        if (leftStone.isPlacedOnBlank(blackStones + whiteStones) && rightStone.isPlacedOnBlank(blackStones + whiteStones)) {
            if (kotlin.math.abs(rightX - leftX) == 5 || kotlin.math.abs(rightY - leftY) == 5) return true
        }
        return false
    }

    private fun Stone.isPlacedOnBlank(stones: Set<Stone>): Boolean = this !in stones
}
