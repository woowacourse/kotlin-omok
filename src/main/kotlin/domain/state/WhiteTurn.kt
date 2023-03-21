package domain.domain.state

import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Stones

class WhiteTurn(stones: Stones) : InProgress(stones) {

    override fun toNextState(position: Position): State {
        val added: Stones.() -> Stones = { this.apply { addStone(position, WHITE) } }
        return when {
            !rule.isEmpty(stones, position) -> WhiteTurn(stones)
            rule.isWhiteWin(stones, position) -> WhiteWin(stones.added())
            else -> BlackTurn(stones.added())
        }
    }
}
