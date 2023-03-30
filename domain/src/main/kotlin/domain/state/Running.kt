package domain.state

import domain.Board
import domain.stone.Point
import domain.stone.Stone
import domain.Team

class Running(override val turn: Team, override val board: Board) : GameState() {

    override fun placeStoneOnBoard(stone: Stone): GameState {
        board.place(turn, stone)
        if (board.hasTeamThatCompletedOmok()) return Finished(turn, board)
        return Running(turn.next(), board)
    }

    override fun canPlace(stone: Stone): Boolean = board.canPlace(turn, stone)

    override fun getLastPoint(): Point? = board.getLastPoint(turn.previous())
}