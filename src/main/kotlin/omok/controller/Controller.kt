package omok.controller

import omok.domain.Board
import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.Stone
import omok.domain.judgement.LineJudgement
import omok.domain.state.State
import omok.domain.state.Turn
import omok.domain.state.Win
import omok.view.GameView

class Controller(private val gameView: GameView) {
    private val board = Board(Player(), Player())
    private var lastPosition: String? = null

    fun gameStart() {
        printStart()
        turn(Turn.Black)
    }

    private fun printStart() = gameView.printStartMessage()

    private fun turn(state: State) {
        return when (state) {
            Turn.Black -> blackTurn()
            Turn.White -> whiteTurn()
            Win.Black -> gameView.printWinMessage(Win.Black)
            Win.White -> gameView.printWinMessage(Win.White)
        }
    }

    private fun blackTurn() {
        val position = readPosition(Turn.Black)
        board.blackPlayer.put(Stone(position))
        board.putStone(position)
        gameView.printBoard(Turn.Black, position)
        return if (lineJudge(board.blackPlayer, position)) turn(Win.Black) else turn(Turn.White)
    }

    private fun whiteTurn() {
        val position = readPosition(Turn.White)
        board.whitePlayer.put(Stone(position))
        board.putStone(position)
        gameView.printBoard(Turn.White, position)
        return if (lineJudge(board.whitePlayer, position)) turn(Win.White) else turn(Turn.Black)
    }

    private fun readPosition(turn: Turn): Position {
        val input = gameView.readPosition(turn, lastPosition)
        val position = Position(HorizontalAxis.valueOf(input.first().toString()), input.slice(1 until input.length).toInt())
        if (!board.isPlaceable(turn, position)) {
            gameView.printRetry()
            readPosition(turn)
        }
        lastPosition = input
        return position
    }

    private fun lineJudge(player: Player, position: Position): Boolean {
        val lineJudgement = LineJudgement(player, position)
        return lineJudgement.check()
    }
}
