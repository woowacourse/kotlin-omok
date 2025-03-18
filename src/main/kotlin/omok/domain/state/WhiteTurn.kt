package omok.domain.state

import omok.domain.Point
import omok.domain.Stones

class WhiteTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): State {
        val newStones = whiteStones + point
        if (newStones.isOmok()) {
            return Finished(blackStones, newStones)
        }
        return BlackTurn(blackStones, newStones)
    }
}
