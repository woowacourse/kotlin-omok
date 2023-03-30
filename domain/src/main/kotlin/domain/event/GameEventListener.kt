package domain.event

import domain.OmokGame

interface GameEventListener {

    fun onGameCreated(omokGame: OmokGame)

    fun onStonePlaced(omokGame: OmokGame)

    fun onGameFinished(omokGame: OmokGame)
}