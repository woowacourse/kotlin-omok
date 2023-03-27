package omok.controller

import omok.domain.BlackStone
import omok.domain.Board
import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.Stone
import omok.domain.WhiteStone
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
        when (state) {
            Turn.Black -> blackTurn()
            Turn.White -> whiteTurn()
            Win.Black -> gameView.printWinMessage(Win.Black)
            Win.White -> gameView.printWinMessage(Win.White)
        }
    }

    private fun blackTurn() {
        val position = readBlackPosition(Turn.Black)
        board.blackPlayer.put(BlackStone(position))
        board.occupyPosition(position)
        gameView.printBoard(Turn.Black, position)
        return if (lineJudge(board.blackPlayer, BlackStone(position))) turn(Win.Black) else turn(
            Turn.White
        )
    }

    private fun whiteTurn() {
        val position = readWhitePosition(Turn.White)
        board.whitePlayer.put(WhiteStone(position))
        board.occupyPosition(position)
        gameView.printBoard(Turn.White, position)
        return if (lineJudge(board.whitePlayer, WhiteStone(position))) turn(Win.White) else turn(
            Turn.Black
        )
    }

    private fun readBlackPosition(turn: Turn): Position {
        val input = gameView.readPosition(turn, lastPosition)
        val position = Position(HorizontalAxis.valueOf(input.first().toString()), input.slice(1 until input.length).toInt())
        if (!board.isBlackPlaceable(position)) {
            gameView.printRetry()
            return readBlackPosition(turn)
        }
        lastPosition = input
        return position
    }

    private fun readWhitePosition(turn: Turn): Position {
        val input = gameView.readPosition(turn, lastPosition)
        val position = Position(HorizontalAxis.valueOf(input.first().toString()), input.slice(1 until input.length).toInt())
        if (!board.isBlackPlaceable(position)) {
            gameView.printRetry()
            return readWhitePosition(turn)
        }
        lastPosition = input
        return position
    }

    private fun lineJudge(player: Player, stone: Stone) = LineJudgement(player, stone).check()
}
