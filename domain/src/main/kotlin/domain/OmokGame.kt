package domain

import domain.event.GameEventManager
import domain.rule.RenjuRule
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team

class OmokGame(
    private val gameEventManager: GameEventManager? = null
) {

    var turn: Team = Team.BLACK
        private set
    val board = Board(RenjuRule)

    init {
        gameEventManager?.notifyCreateEvent(this)
    }

    fun place(stone: Stone) {
        require(canPlace(stone)) { STONE_PLACE_ERROR }
        board.place(turn, stone)
        turn = turn.next()
        if (isFinished()) {
            gameEventManager?.notifyFinishedEvent(this)
        } else {
            gameEventManager?.notifyStonePlaceEvent(this)
        }
    }

    fun canPlace(stone: Stone): Boolean = isFinished().not() && board.canPlace(turn, stone)

    fun isFinished(): Boolean = board.hasTeamThatCompletedOmok()

    fun getWinner(): Team = board.getTeamThatCompletedOmok()

    fun getLastPoint(): Point? = board.getLastPoint(turn.previous())

    companion object {
        private const val STONE_PLACE_ERROR = "돌을 둘 수 없습니다."
    }
}
