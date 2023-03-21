package omok.domain

import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.judgment.WinningReferee

class Game(private val board: Board, private var turn: Turn, private val winningReferee: WinningReferee) {
    fun start(
        onStart: (board: Board) -> Unit,
        wantPosition: (latestPosition: Position?, turn: Turn) -> Pair<Int, Int>,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit,
    ) {
        onStart(board)
        play(wantPosition, onTurn)
        finish(onFinish)
    }

    private fun play(
        wantPosition: (latestPosition: Position?, turn: Turn) -> Pair<Int, Int>,
        onTurn: (board: Board) -> Unit,
    ) {
        playUntilWinnerAppears(null, wantPosition, onTurn)
    }

    private tailrec fun playUntilWinnerAppears(
        position: Position?,
        wantPosition: (latestPosition: Position?, turn: Turn) -> Pair<Int, Int>,
        onTurn: (board: Board) -> Unit,
    ) {
        val selectedPosition = place(position, wantPosition)
        onTurn(board)

        if (winningReferee.hasFiveOrMoreStoneInRow(board.positions, selectedPosition)) return
        turn.changeTurn()
        playUntilWinnerAppears(selectedPosition, wantPosition, onTurn)
    }

    private fun finish(onFinish: (turn: Turn) -> Unit) {
        onFinish(turn)
    }

    private fun place(
        latestPosition: Position?,
        wantPosition: (latestPosition: Position?, turn: Turn) -> Pair<Int, Int>,
    ): Position {
        val selectedPosition = Position(wantPosition(latestPosition, turn))
        if (board.positions[selectedPosition] != null) {
            place(latestPosition, wantPosition)
        } else {
            board.place(selectedPosition, turn.now)
        }
        return selectedPosition
    }
}
