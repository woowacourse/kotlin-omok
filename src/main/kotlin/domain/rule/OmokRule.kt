package domain.rule

import domain.stone.Stone
import domain.stone.StoneType

interface OmokRule {

    fun isWinCondition(board: List<List<StoneType?>>, stone: Stone): Boolean

    fun isForbidden(board: List<List<StoneType?>>, stone: Stone): Boolean
}
