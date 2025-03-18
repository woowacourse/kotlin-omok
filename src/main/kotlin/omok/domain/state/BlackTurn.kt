package omok.domain.state

import omok.domain.Point
import omok.domain.Stones

class BlackTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): State {
        val newStones = blackStones + point
        if (newStones.isOmok()) {
            return Finished(newStones, whiteStones)
        }
        return WhiteTurn(newStones, whiteStones)
    }
}
