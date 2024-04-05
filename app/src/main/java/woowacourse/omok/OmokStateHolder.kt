package woowacourse.omok

import omok.model.OmokGameState
import omok.model.entity.Point

class OmokStateHolder(
    private var omokGameState: OmokGameState,
) {
    private val listeners: MutableList<OmokStateListener> = mutableListOf()

    fun proceed(point: Point) {
        if (omokGameState.isFinished()) {
            return
        }
        replaceState(omokGameState.run(point))
    }

    fun replaceState(newOmokState: OmokGameState) {
        omokGameState = newOmokState
        broadcast()
    }

    private fun broadcast() =
        listeners.forEach {
            it.listen(omokGameState)
        }

    fun subscribedBy(listener: OmokStateListener) {
        listeners.add(listener)
    }
}

interface OmokStateListener {
    fun listen(omokState: OmokGameState)
}
