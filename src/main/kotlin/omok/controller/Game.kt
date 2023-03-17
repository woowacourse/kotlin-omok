package omok.controller

import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.board.toPosition
import omok.domain.judgment.BlackReferee
import omok.domain.judgment.WinningReferee
import omok.domain.player.Black
import omok.domain.player.White
import omok.view.InputView
import omok.view.OutputView

class Game(
    private val inputView: InputView,
    private val outputView: OutputView
) {
    fun start() {
        val board = Board()
        outputView.printStart(board)
        play(board, turn = Turn(listOf(Black, White)))
    }

    private fun play(board: Board, turn: Turn) {
        playUntilWinnerAppears(board, turn, null)
    }

    private fun playUntilWinnerAppears(board: Board, turn: Turn, position: Position?) {
        val selectedPosition = placeStone(board, turn, position)
        outputView.printBoard(board)

        if (WinningReferee().hasFiveOrMoreStoneInRow(board.positions, selectedPosition)) return finish(turn)
        turn.changeTurn()
        playUntilWinnerAppears(board, turn, selectedPosition)
    }

    private fun placeStone(board: Board, turn: Turn, position: Position?): Position {
        return runCatching {
            val selectedPosition = inputView.readPosition(position, turn).toPosition()
            board.placeStone(selectedPosition, turn.now, referee = BlackReferee())
            selectedPosition
        }.getOrElse {
            println(it.message)
            placeStone(board, turn, position)
        }
    }

    private fun finish(turn: Turn) {
        outputView.printWinner(turn)
    }
}
