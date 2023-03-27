package omok

import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.judgment.WinningReferee
import omok.domain.player.Stone

class OmokGame(
    val board: Board,
    private val turn: Turn,
    private val referee: WinningReferee = WinningReferee()
) {

    var isFinished: Boolean = false
        private set
    val currentStone: Stone
        get() = turn.now

    fun place(position: Position): Boolean {
        if (board.positions[position] != null) return false
        board.place(position, turn.now)
        return true
    }

    fun changeTurn() {
        turn.nextTurn()
    }

    fun checkWinner(position: Position) {
        val isWinner = referee.hasFiveOrMoreStoneInRow(board.positions, position)
        if (isWinner) {
            isFinished = true
        }
    }
}
