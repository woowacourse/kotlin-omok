package omok.controller

import omok.HorizontalAxis
import omok.OmokGame
import omok.Position
import omok.state.Fail
import omok.state.State
import omok.state.Turn
import omok.state.Win
import omok.view.GameView

class Controller(private val gameView: GameView, private val omokGame: OmokGame) {

    fun gameStart() {
        printStart()
        val win = gameOn()
        gameOver(win)
    }

    private fun printStart() = gameView.printStartMessage()

    private fun gameOn(): Win {
        var state: State = Turn.Black
        while (state !is Win) {
            val position = readPosition(state as Turn)
            val presentState = omokGame.takeTurn(state, position)
            if (presentState == Fail) {
                gameView.printRetry()
            } else {
                gameView.printBoard(state, position)
                state = presentState
            }
        }
        return state
    }

    private fun readPosition(turn: Turn): Position {
        val input = gameView.readPosition(turn, omokGame.lastPosition)
        return Position(
            HorizontalAxis.valueOf(input.first().toString()),
            input.slice(1 until input.length).toInt()
        )
    }

    private fun gameOver(win: Win): Win {
        gameView.printWinMessage(win)
        return win
    }
}
