package domain.event

import domain.OmokGame

interface FinishEventListener {

    fun notifyFinishEventHasOccurred(omokGame: OmokGame)
}
