package domain.event

import domain.OmokGame

class FinishEventManager {

    private val listeners = mutableListOf<FinishEventListener>()

    fun add(listener: FinishEventListener) {
        listeners.add(listener)
    }

    fun notify(omokGame: OmokGame) {
        listeners.forEach { it.notifyFinishEventHasOccurred(omokGame) }
    }
}
