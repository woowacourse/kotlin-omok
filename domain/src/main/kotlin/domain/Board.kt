package domain

import domain.rule.Rule
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Stones
import domain.stone.Team

class Board(private val rule: Rule) {

    private val boardStones = Team.values().associateWith { Stones() }
    var lastStonePoint: Point? = null
    val stonePlacedPoints: Set<Point>
        get() = boardStones.values.flatMap { it.stones.map { stone -> stone.point } }.toSet()

    operator fun get(team: Team): Stones = boardStones[team]!!

    fun place(team: Team, stone: Stone) {
        require(pointIsWithinBoardRange(stone.point)) { STONE_POINT_RANGE_ERROR.format(stone.point) }
        require(stone.point !in stonePlacedPoints) { STONE_OVERLAP_ERROR.format(stone.point) }
        boardStones[team]!!.add(stone)
        lastStonePoint = stone.point
        check(rule.isObeyed(this)) { RULE_NOT_OBEY_ERROR }
    }

    fun canPlace(team: Team, stone: Stone): Boolean = pointIsWithinBoardRange(stone.point) &&
        stone.point !in stonePlacedPoints &&
        rule.isObeyed(this.copy().apply { place(team, stone) })

    fun isPlaced(team: Team, stone: Stone): Boolean = stone in boardStones[team]!!

    fun hasTeamThatCompletedOmok(): Boolean = boardStones.values.any(Stones::completeOmok)

    fun getTeamThatCompletedOmok(): Team =
        boardStones.keys.first { boardStones[it]!!.completeOmok() }

    private fun copy(): Board {
        val copyBoard = Board(rule)
        Team.values().forEach {
            boardStones[it]!!.stones.forEach { stone -> copyBoard.place(it, stone) }
        }
        return copyBoard
    }

    companion object {
        private const val STONE_POINT_RANGE_ERROR = "돌이 오목판의 범위를 벗어났습니다.\n돌의 좌표: %s"
        private const val STONE_OVERLAP_ERROR = "이미 놓여진 위치에 돌을 둘 수 없습니다.\n돌의 좌표: %s"
        private const val RULE_NOT_OBEY_ERROR = "규칙을 어겼습니다."

        const val BOARD_SIZE = 15

        fun pointIsWithinBoardRange(point: Point): Boolean =
            point.x in ('A' until 'A' + BOARD_SIZE) && point.y in (1..BOARD_SIZE)
    }
}
