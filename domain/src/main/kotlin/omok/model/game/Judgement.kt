package omok.model.game

import StateJudgement
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

object Judgement {
    fun judge(board: Board, goStone: GoStone): State {
        val rule = StateJudgement(board, goStone)
        val coordinate = goStone.coordinate
        val color = goStone.color

        if (color == GoStoneColor.WHITE) {
            return rule.checkWhiteWin(coordinate)
        }

        return checkByBlackStone(rule, coordinate)
    }

    private fun checkByBlackStone(rule: StateJudgement, coordinate: Coordinate): State {
        var state = rule.checkBlackWin(coordinate)
        if (!state.isRunning) return state

        state = rule.countOpenThrees(coordinate)
        if (state.isForbidden) return state

        rule.countOpenFours(coordinate)
        return state
    }
}
