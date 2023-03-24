package domain.event

import domain.OmokGame

interface StartEventListener {

    fun notifyStartEventHasOccurred(omokGame: OmokGame)
}
