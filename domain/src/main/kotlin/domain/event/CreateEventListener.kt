package domain.event

import domain.OmokGame

interface CreateEventListener {

    fun notifyCreateEventHasOccurred(omokGame: OmokGame)
}
