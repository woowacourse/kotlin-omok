package domain.rule

import domain.stone.Stone
import domain.stone.Stones

interface OmokRule {
    fun check(blackStones: Stones, whiteStones: Stones, startStone: Stone): Boolean
}
