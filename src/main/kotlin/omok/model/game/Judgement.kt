package omok.model.game

import OmokRuleConverter
import omok.model.state.State
import omok.model.state.Stay
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

object Judgement {
    fun judge(board: Board, goStone: GoStone): State {
        val rule = OmokRuleConverter(board)
        val coordinate = goStone.coordinate
        val color = goStone.color

        if (color == GoStoneColor.WHITE) {
            return rule.checkWhiteWin(coordinate)
        }

        var state = rule.checkBlackWin(coordinate)
        if (state !is Stay) return state

        state = rule.countOpenThrees(coordinate)
        if (state !is Stay) return state

        rule.countOpenFours(coordinate)
        return state
    }
}
