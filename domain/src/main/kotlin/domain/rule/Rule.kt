package domain.rule

import domain.stone.Point
import domain.stone.Stones
import domain.Team

abstract class Rule(private val constraints: Map<Team, List<StonesConstraint>>) {

    fun isObeyed(boardStones: Map<Team, Stones>): Boolean {
        val stonePlacedPoints = boardStones.values.fold(setOf<Point>()) { _, stones ->
            stones.stones.map { stone -> stone.point }.toSet()
        }
        return boardStones.entries.all { (team, stones) ->
            stones.satisfy(constraints[team] ?: listOf(), stonePlacedPoints)
        }
    }

    private fun Stones.satisfy(
        constraints: List<StonesConstraint>, forbiddenPoints: Set<Point>
    ): Boolean = constraints.all { it.isSatisfied(this, forbiddenPoints) }
}
