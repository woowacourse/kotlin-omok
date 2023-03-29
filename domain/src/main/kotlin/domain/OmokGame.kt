package domain

import domain.event.CreateEventManager
import domain.event.FinishEventManager
import domain.event.PlaceStoneEventManager
import domain.rule.RenjuRule
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team

class OmokGame(
    createEventManager: CreateEventManager? = null,
    private val placeStoneEventManager: PlaceStoneEventManager? = null,
    private val finishEventManager: FinishEventManager? = null,
) {

    var turn: Team = Team.BLACK
        private set
    val board = Board(RenjuRule)
    val lastPoint: Point?
        get() = board.lastStonePoint

    init {
        createEventManager?.notify(this)
    }

    fun place(stone: Stone) {
        require(canPlace(stone)) { STONE_PLACE_ERROR }
        board.place(turn, stone)
        turn = turn.next()
        if (isFinished()) {
            finishEventManager?.notify(this)
        } else {
            placeStoneEventManager?.notify(this)
        }
    }

    fun canPlace(stone: Stone): Boolean = isFinished().not() && board.canPlace(turn, stone)

    fun isFinished(): Boolean = board.hasTeamThatCompletedOmok()

    fun getWinner(): Team = board.getTeamThatCompletedOmok()

    companion object {
        private const val STONE_PLACE_ERROR = "돌을 둘 수 없습니다."
    }
}
