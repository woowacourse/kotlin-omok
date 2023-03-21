package omok.model.state

import OmokRuleConverter
import omok.model.game.Board
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

object Judgement {
    fun judge(board: Board, goStone: GoStone): State {
        val rule = OmokRuleConverter(board, goStone.color)
        val coordinate = goStone.coordinate
        val color = goStone.color

        if (color == GoStoneColor.WHITE) {
            return rule.checkWhiteWin(coordinate)
        }

        return checkByBlackStone(rule, coordinate)
    }

    private fun checkByBlackStone(rule: OmokRuleConverter, coordinate: Coordinate): State {
        var state = rule.checkBlackWin(coordinate)
        if (state != State.Stay) return state

        state = rule.countOpenThrees(coordinate)
        if (state != State.Stay) return state

        rule.countOpenFours(coordinate)
        return state
    }
}
