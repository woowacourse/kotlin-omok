package omok.controller

import omok.HorizontalAxis
import omok.OmokGame
import omok.Position
import omok.state.State
import omok.state.Turn
import omok.state.Win
import omok.view.GameView

class Controller(private val gameView: GameView, private val omokGame: OmokGame) {

    fun gameStart() {
        printStart()
        takeTurn(Turn.Black)
    }

    private fun printStart() = gameView.printStartMessage()

    private fun takeTurn(state: State): State {
        return when (state) {
            Turn.Black -> takeTurn(gameOn(state as Turn))
            Turn.White -> takeTurn(gameOn(state as Turn))
            Win.Black -> gameOver(Win.Black)
            Win.White -> gameOver(Win.White)
        }
    }

    private fun gameOn(turn: Turn): State {
        val position = readPosition(turn)
        if (!omokGame.board.isPlaceable(turn, position)) {
            // warning
            return turn
        }
        gameView.printBoard(turn, position)
        return when (turn) {
            Turn.Black -> omokGame.blackTurn(position)
            Turn.White -> omokGame.whiteTurn(position)
        }
    }

    private fun readPosition(turn: Turn): Position {
        val input = gameView.readPosition(turn, omokGame.lastPosition)
        val position = Position(HorizontalAxis.valueOf(input.first().toString()), input.slice(1 until input.length).toInt())
        if (!omokGame.board.isPlaceable(turn, position)) {
            gameView.printRetry()
            return readPosition(turn)
        }
        omokGame.lastPosition = input
        return position
    }

    private fun gameOver(win: Win): Win {
        gameView.printWinMessage(win)
        return win
    }
}
