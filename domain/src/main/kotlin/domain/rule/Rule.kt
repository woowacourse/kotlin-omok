package domain.rule

import domain.Board
import domain.stone.Team

abstract class Rule(private val constraints: Map<Team, List<StonesConstraint>>) {

    fun isObeyed(board: Board): Boolean =
        Team.values().all {
            constraints[it]?.all { constraint ->
                constraint.isSatisfied(board[it], board.stonePlacedPoints)
            } ?: true
        }

}