package omok.domain

import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.board.toPosition
import omok.domain.judgment.Referee

class Game(private val board: Board, private var turn: Turn, private val referee: Referee) {
    fun start(
        onStart: (board: Board) -> Unit,
        wantPosition: (position: Position?, turn: Turn) -> String,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit
    ) {
        onStart(board)
        play(wantPosition, onTurn, onFinish)
    }

    private fun play(
        wantPosition: (position: Position?, turn: Turn) -> String,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit
    ) {
        playUntilWinnerAppears(null, wantPosition, onTurn, onFinish)
    }

    private tailrec fun playUntilWinnerAppears(
        position: Position?,
        wantPosition: (position: Position?, turn: Turn) -> String,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit
    ) {
        val selectedPosition = wantPosition(position, turn).toPosition()
        board.place(selectedPosition, turn.now)
        onTurn(board)

        if (referee.hasFiveOrMoreStoneInRow(board.positions, selectedPosition)) return finish(onFinish)
        turn = turn.next()
        playUntilWinnerAppears(selectedPosition, wantPosition, onTurn, onFinish)
    }

    private fun finish(onFinish: (turn: Turn) -> Unit) {
        onFinish(turn)
    }
}
