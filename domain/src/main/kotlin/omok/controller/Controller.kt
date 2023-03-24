package omok.controller

import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.judgment.WinningReferee
import omok.domain.player.Stone
import omok.view.InputView
import omok.view.OutputView

class Controller(private val inputView: InputView, private val outputView: OutputView) {
    fun start() {
        startGame(Board(), Turn(setOf(Stone.BLACK, Stone.WHITE)), WinningReferee())
    }

    private fun startGame(
        board: Board,
        turn: Turn,
        winningReferee: WinningReferee
    ) {
        outputView.printStart(board)
        play(board, turn, winningReferee)
        outputView.printWinner(turn)
    }

    private fun play(
        board: Board,
        turn: Turn,
        winningReferee: WinningReferee
    ) {
        playUntilWinnerAppears(null, board, turn, winningReferee)
    }

    private tailrec fun playUntilWinnerAppears(
        position: Position?,
        board: Board,
        turn: Turn,
        winningReferee: WinningReferee
    ) {
        val selectedPosition = place(position, turn, board)
        outputView.printBoard(board)

        if (winningReferee.hasFiveOrMoreStoneInRow(board.positions, selectedPosition)) return
        turn.nextTurn()
        playUntilWinnerAppears(selectedPosition, board, turn, winningReferee)
    }

    private fun place(
        latestPosition: Position?,
        turn: Turn,
        board: Board
    ): Position {
        val input = inputView.readPosition(latestPosition, turn)
        val selectedPosition = Position(input)
        if (board.positions[selectedPosition] != null) {
            place(latestPosition, turn, board)
        } else {
            board.place(selectedPosition, turn.now)
        }
        return selectedPosition
    }
}
