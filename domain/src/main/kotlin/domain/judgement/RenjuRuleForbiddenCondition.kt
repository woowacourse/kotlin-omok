package domain.judgement

import domain.library.BlackReferee
import domain.library.PlacementReferee
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class RenjuRuleForbiddenCondition(private val referee: PlacementReferee = BlackReferee()) :
    ForbiddenCondition {
    override fun isForbidden(board: Map<Position, Color?>, newStone: Stone): Boolean {
        if (newStone.color == Color.BLACK && referee.isForbiddenPlacement(
                board,
                newStone.position
            )
        ) return true
        return false
    }
}
