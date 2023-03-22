package domain.library

import domain.Position
import domain.domain.Stones

interface Rule {
    fun isEmpty(stones: Stones, position: Position): Boolean
    fun isBlackWin(stones: Stones, position: Position): Boolean
    fun isWhiteWin(stones: Stones, position: Position): Boolean
    fun isBlackForbidden(stones: Stones, position: Position): Boolean
}
