package domain.rule

import domain.stone.StoneColor
import domain.stone.Stones

interface OmokRule {
    fun check(blackStones: Stones, whiteStones: Stones, turn: StoneColor, currentWin: Boolean): Boolean
}
