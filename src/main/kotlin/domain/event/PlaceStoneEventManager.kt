package domain.event

import domain.OmokGame

class PlaceStoneEventManager {

    private val listeners = mutableListOf<PlaceStoneEventListener>()

    fun add(listener: PlaceStoneEventListener) {
        listeners.add(listener)
    }

    fun notify(omokGame: OmokGame) {
        listeners.forEach { it.notifyPlaceStoneEventHasOccurred(omokGame) }
    }
}
