package omok.model.rule

import omok.model.Board
import omok.model.Position
import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.winning.ContinualStonesWinningCondition

class GamePlayingRules private constructor(
    private val continualStonesWinningCondition: ContinualStonesWinningCondition,
    private val forbiddenPlaces: ForbiddenPlaces,
) {
    init {
        if (!continualStonesWinningCondition.canHaveDoubleRule()) {
            require(!forbiddenPlaces.hasDoubleRule()) { "승리 조건이 오목 미만일 경우에는 더블 규칙(3-3, 4-4)을 가질 수 없습니다." }
        }
        if (!continualStonesWinningCondition.canHaveOverlineRule()) {
            require(!forbiddenPlaces.hasOverlineRule()) { "승리 조건이 N목 이상을 완성일 경우에는 장목 규칙을 가질 수 없습니다." }
        }
    }

    fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean {
        if (forbiddenPlaces.hasNoRule()) return true
        return forbiddenPlaces.list.all { it.availablePosition(board, position) }
    }

    fun isWin(
        board: Board,
        position: Position,
    ): Boolean = continualStonesWinningCondition.isWin(board, position)

    companion object {
        fun from(
            continualStonesWinningCondition: ContinualStonesWinningCondition,
            forbiddenPlaces: ForbiddenPlaces,
        ): GamePlayingRules {
            return GamePlayingRules(
                continualStonesWinningCondition,
                forbiddenPlaces.initOverlineStandard(continualStonesWinningCondition.continualStonesStandard),
            )
        }
    }
}
