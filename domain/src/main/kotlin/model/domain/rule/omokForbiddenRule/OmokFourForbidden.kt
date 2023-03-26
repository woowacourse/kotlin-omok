package model.domain.rule.omokForbiddenRule

import model.domain.rule.OtherForbiddenRule
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokFourForbidden(board: Board, currentStone: Stone) : OmokForbiddenRule,
    OmokForbiddenUseOtherRule(board, currentStone) {

    override fun isForbidden(location: Location): Boolean {
        val x = location.coordinationY.value
        val y = location.coordinationX.value
        val otherRule = OtherForbiddenRule(convertBoard, stoneState)
        return otherRule.countOpenFours(x, y) >= 2
    }
}
