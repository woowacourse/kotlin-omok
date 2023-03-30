package domain.event

import domain.OmokGame

class GameEventManager {

    private val listeners: MutableList<GameEventListener> = mutableListOf()

    fun add(listener: GameEventListener) {
        listeners.add(listener)
    }

    fun notifyCreateEvent(omokGame: OmokGame) {
        listeners.forEach { it.onGameCreated(omokGame) }
    }

    fun notifyStonePlaceEvent(omokGame: OmokGame) {
        listeners.forEach { it.onStonePlaced(omokGame) }
    }

    fun notifyFinishedEvent(omokGame: OmokGame) {
        listeners.forEach { it.onGameFinished(omokGame) }
    }
}