package omok.controller

import omok.domain.BlackStone
import omok.domain.Board
import omok.domain.HorizontalAxis
import omok.domain.LineJudgement
import omok.domain.Player
import omok.domain.Position
import omok.domain.WhiteStone
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

    private fun turn(state: State) {
        return when (state) {
            Turn.Black -> blackTurn()
            Turn.White -> whiteTurn()
            Win.Black -> gameView.printWinMessage(Win.Black)
            Win.White -> gameView.printWinMessage(Win.White)
        }
    }

    private fun printStart() = gameView.printStartMessage()

    private fun blackTurn() {
        val input = gameView.readPosition(Turn.Black, lastPosition)
        val position = Position(HorizontalAxis.valueOf(input[0].toString()), input.slice(1 until input.length).toInt())
        if (!board.isPlaceable(position)) {
            gameView.printRetry()
            blackTurn()
        }
        lastPosition = input
        board.blackPlayer.put(BlackStone(position))
        board.putStone(position)
        gameView.printBoard(Turn.Black, position)
        return if (lineJudge(board.blackPlayer, position)) turn(Win.Black) else turn(Turn.White)
    }

    private fun lineJudge(player: Player, position: Position): Boolean {
        val lineJudgement = LineJudgement(player, position)
        return lineJudgement.checkHorizontal() ||
            lineJudgement.checkVertical() ||
            lineJudgement.checkMajorDiagonal() ||
            lineJudgement.checkSubDiagonal()
    }

    private fun whiteTurn() {
        val input = gameView.readPosition(Turn.White, lastPosition)
        val position = Position(HorizontalAxis.valueOf(input[0].toString()), input.slice(1 until input.length).toInt())
        if (!board.isPlaceable(position)) {
            gameView.printRetry()
            whiteTurn()
        }
        lastPosition = input
        board.whitePlayer.put(WhiteStone(position))
        board.putStone(position)
        gameView.printBoard(Turn.White, position)
        return if (lineJudge(board.whitePlayer, position)) turn(Win.White) else turn(Turn.Black)
    }
}
