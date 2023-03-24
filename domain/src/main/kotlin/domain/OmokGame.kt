package domain

import domain.event.FinishEventManager
import domain.event.PlaceStoneEventManager
import domain.event.StartEventManager
import domain.state.*
import domain.stone.Point
import domain.stone.Stone

class OmokGame(
    startEventManager: StartEventManager? = null,
    private val placeStoneEventManager: PlaceStoneEventManager? = null,
    private val finishEventManager: FinishEventManager? = null,
) {

    private var state: State = BlackTurn(setOf(), setOf())

    init {
        startEventManager?.notify(this)
    }

    fun place(stone: Stone) {
        state = state.put(stone)
        if (state is Running) placeStoneEventManager?.notify(this)
        if (state is Finished) finishEventManager?.notify(this)
    }

    fun canPlace(stone: Stone): Boolean = state.canPut(stone)

    fun isFinished(): Boolean = state is Finished

    fun isBlackTurn(): Boolean = state is BlackTurn

    fun isBlackWin(): Boolean = state is BlackWin

    fun blackStoneIsPlaced(stone: Stone): Boolean = state.blackStones.contains(stone)

    fun whiteStoneIsPlaced(stone: Stone): Boolean = state.whiteStones.contains(stone)

    fun getPointOfLastStonePlaced(): Point? =
        if (state is WhiteTurn || state is BlackWin) state.blackStones.lastOrNull()?.point else state.whiteStones.lastOrNull()?.point

    companion object {
        const val BOARD_SIZE = 15

        fun boardContains(point: Point): Boolean =
            point.x in ('A' until 'A' + BOARD_SIZE) && point.y in (1..BOARD_SIZE)
    }
}
