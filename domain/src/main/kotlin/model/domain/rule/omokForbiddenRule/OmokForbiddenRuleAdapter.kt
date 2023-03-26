package model.domain.rule.omokForbiddenRule

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokForbiddenRuleAdapter(board: Board, private val currentStone: Stone) :
    OmokForbiddenRule {

    private val forbiddenRulesByStone = mapOf(
        Stone.BLACK to listOf(
            OmokThreeForbidden(board, currentStone),
            OmokFourForbidden(board, currentStone),
            OmokLongForbidden(board, currentStone)
        ),
        Stone.WHITE to listOf()
    )

    override fun isForbidden(location: Location): Boolean {
        return forbiddenRulesByStone[currentStone]?.any {
            it.isForbidden(location)
        } ?: false
    }
}
