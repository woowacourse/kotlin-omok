package domain.domain.state

import domain.CoordinateState.BLACK
import domain.Position
import domain.domain.Stones

class BlackTurn(stones: Stones) : InProgress(stones) {

    override fun toNextState(position: Position): State {
        val added: Stones.() -> Stones = { this.apply { addStone(position, BLACK) } }
        return when {
            !rule.isEmpty(stones, position) -> BlackTurn(stones)
            rule.isBlackWin(stones, position) -> BlackWin(stones.added())
            rule.isBlackForbidden(stones, position) -> BlackTurn(stones)
            else -> WhiteTurn(stones.added())
        }
    }
}
