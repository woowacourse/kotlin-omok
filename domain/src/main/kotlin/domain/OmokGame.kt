package domain

import domain.event.GameEventManager
import domain.rule.RenjuRule
import domain.state.Finished
import domain.state.GameState
import domain.state.Running
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team

class OmokGame(
    private val gameEventManager: GameEventManager? = null
) {

    private var gameState: GameState = Running(Team.BLACK, Board(RenjuRule))

    init {
        gameEventManager?.notifyCreateEvent(this)
    }

    fun place(stone: Stone) {
        require(canPlace(stone)) { STONE_PLACE_ERROR }
        gameState = gameState.placeStoneOnBoard(stone)
        if (gameState is Finished) {
            gameEventManager?.notifyFinishedEvent(this)
        } else {
            gameEventManager?.notifyStonePlaceEvent(this)
        }
    }

    fun canPlace(stone: Stone): Boolean = gameState.canPlace(stone)

    fun isPlaced(team: Team, stone: Stone): Boolean = gameState.isPlaced(team, stone)

    fun isFinished(): Boolean = gameState is Finished

    fun getWinner(): Team = if (gameState is Finished) gameState.turn
        else throw IllegalStateException(NO_WINNER_CAN_BE_DETERMINED_ERROR)

    fun getLastPoint(): Point? = gameState.getLastPoint()

    fun getBoard(): Board = gameState.board

    fun getTurn(): Team = gameState.turn

    companion object {
        private const val STONE_PLACE_ERROR = "돌을 둘 수 없습니다."
        private const val NO_WINNER_CAN_BE_DETERMINED_ERROR = "승자를 가릴 수 없는 상태입니다."
    }
}
