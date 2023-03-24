package domain.event

import domain.OmokGame

interface PlaceStoneEventListener {

    fun notifyPlaceStoneEventHasOccurred(omokGame: OmokGame)
}
