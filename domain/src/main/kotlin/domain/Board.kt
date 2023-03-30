package domain

import domain.rule.Rule
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Stones

class Board(private val rule: Rule) {

    private val boardStones = Team.associateWith { Stones() }

    operator fun get(team: Team): Stones = boardStones[team] ?: Stones()

    fun place(team: Team, stone: Stone) {
        require(pointIsWithinBoardRange(stone.point)) { STONE_POINT_RANGE_ERROR.format(stone.point) }
        require(stone.point !in getStonePlacedPoints()) { STONE_OVERLAP_ERROR.format(stone.point) }
        boardStones[team]?.add(stone)
            ?: throw IllegalArgumentException(NOT_REGISTERED_TEAM_ERROR.format(team))
        check(rule.isObeyed(boardStones)) { RULE_NOT_OBEY_ERROR }
    }

    fun canPlace(team: Team, stone: Stone): Boolean = pointIsWithinBoardRange(stone.point) &&
            stone.point !in getStonePlacedPoints() &&
            rule.isObeyed(nextBoard(team, stone))

    private fun nextBoard(team: Team, nextStone: Stone): Map<Team, Stones> =
        Team.values().associateWith {
            val stones = boardStones[it]?.copy() ?: throw IllegalArgumentException(
                NOT_REGISTERED_TEAM_ERROR.format(team)
            )
            if (it == team) stones.add(nextStone)
            stones
        }

    fun isPlaced(team: Team, stone: Stone): Boolean = stone in (boardStones[team] ?: Stones())

    fun hasTeamThatCompletedOmok(): Boolean = boardStones.values.any(Stones::completeOmok)

    private fun getStonePlacedPoints(): Set<Point> =
        boardStones.values.flatMap { it.stones.map { stone -> stone.point } }.toSet()

    fun getLastPoint(team: Team): Point? = boardStones[team]?.lastPoint

    companion object {
        private const val STONE_POINT_RANGE_ERROR = "돌이 오목판의 범위를 벗어났습니다.\n돌의 좌표: %s"
        private const val STONE_OVERLAP_ERROR = "이미 놓여진 위치에 돌을 둘 수 없습니다.\n돌의 좌표: %s"
        private const val NOT_REGISTERED_TEAM_ERROR = "등록되지 않은 팀입니다.\n등록 되지 않은 팀: %s"
        private const val RULE_NOT_OBEY_ERROR = "규칙을 어겼습니다."

        const val BOARD_SIZE = 15

        fun pointIsWithinBoardRange(point: Point): Boolean =
            point.x in ('A' until 'A' + BOARD_SIZE) && point.y in (1..BOARD_SIZE)
    }
}
