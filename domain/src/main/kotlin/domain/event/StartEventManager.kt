package domain.event

import domain.OmokGame

class StartEventManager {

    private val listeners = mutableListOf<StartEventListener>()

    fun add(listener: StartEventListener) {
        listeners.add(listener)
    }

    fun notify(omokGame: OmokGame) {
        listeners.forEach { it.notifyStartEventHasOccurred(omokGame) }
    }
}
