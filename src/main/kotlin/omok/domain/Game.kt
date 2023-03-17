package omok.domain

import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.board.toPosition
import omok.domain.judgment.WinningReferee

class Game(
    private val board: Board,
    private var turn: Turn,
    private val winningReferee: WinningReferee
) {
    fun start(
        onStart: (board: Board) -> Unit,
        wantPosition: (position: Position?, turn: Turn) -> String?,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit
    ) {
        onStart(board)
        play(wantPosition, onTurn, onFinish)
    }

    private fun play(
        wantPosition: (position: Position?, turn: Turn) -> String?,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit
    ) {
        playUntilWinnerAppears(null, wantPosition, onTurn, onFinish)
    }

    private tailrec fun playUntilWinnerAppears(
        position: Position?,
        wantPosition: (position: Position?, turn: Turn) -> String?,
        onTurn: (board: Board) -> Unit,
        onFinish: (turn: Turn) -> Unit
    ) {
        val selectedPosition = placeStone(position, wantPosition)
        onTurn(board)

        if (winningReferee.hasFiveOrMoreStoneInRow(board.positions, selectedPosition)) return finish(onFinish)
        turn = turn.next()
        playUntilWinnerAppears(selectedPosition, wantPosition, onTurn, onFinish)
    }

    private fun finish(onFinish: (turn: Turn) -> Unit) {
        onFinish(turn)
    }

    private fun placeStone(
        position: Position?,
        wantPosition: (position: Position?, turn: Turn) -> String?
    ): Position {
        return runCatching {
            val selectedPosition = wantPosition(position, turn).toPosition()
            board.placeStone(selectedPosition, turn.now)
            selectedPosition
        }.getOrElse {
            println(it.message)
            placeStone(position, wantPosition)
        }
    }
}
