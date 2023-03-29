package domain.event

import domain.OmokGame

class CreateEventManager {

    private val listeners = mutableListOf<CreateEventListener>()

    fun add(listener: CreateEventListener) {
        listeners.add(listener)
    }

    fun notify(omokGame: OmokGame) {
        listeners.forEach { it.notifyCreateEventHasOccurred(omokGame) }
    }
}
