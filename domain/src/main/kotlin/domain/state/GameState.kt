package domain.state

import domain.Board
import domain.stone.Point
import domain.stone.Stone
import domain.Team

abstract class GameState {
    abstract val turn: Team
    abstract val board: Board

    abstract fun placeStoneOnBoard(stone: Stone): GameState

    abstract fun canPlace(stone: Stone): Boolean

    fun isPlaced(team: Team, stone: Stone): Boolean = board.isPlaced(team, stone)

    abstract fun getLastPoint(): Point?
}