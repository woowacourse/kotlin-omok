package omok

import omok.state.State
import omok.state.Turn
import omok.state.Win

class Controller(private val gameView: GameView, private val omokGame: OmokGame) {

    fun gameStart() {
        printStart()
        takeTurn(Turn.Black)
    }

    private fun printStart() = gameView.printStartMessage()

    private fun takeTurn(state: State) {
        return when (state) {
            Turn.Black -> takeTurn(omokGame.blackTurn(readPosition(Turn.Black), gameView::printBoard))
            Turn.White -> takeTurn(omokGame.whiteTurn(readPosition(Turn.White), gameView::printBoard))
            Win.Black -> gameView.printWinMessage(Win.Black)
            Win.White -> gameView.printWinMessage(Win.White)
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
}
